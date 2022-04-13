package br.com.bioconnect.BioAcesso.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioconnect.BioAcesso.Exception.MessageBroadcastException;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.MessageBroadcast;
import br.com.bioconnect.BioAcesso.model.User;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;
import br.com.bioconnect.BioAcesso.repository.IMessageBroadcastRepository;
import br.com.bioconnect.BioAcesso.repository.IUserRepository;
import br.com.bioconnect.BioAcesso.service.message.FacialMessage;
import br.com.bioconnect.BioAcesso.util.MessageBroadcastTypeEnum;

@Service
public class CadastroRemotoService {

	@Autowired
	private FacialMessage facialMessage;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IMessageBroadcastRepository messageBroadcastRepository;
	
	@Autowired
	private IDeviceRepository deviceRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	public Boolean doRemoteEnroll (Device device, User user) {
		HttpRequest req;
		HttpResponse<String> response;
		
		try {
			// solicitação de captura de imagem
			req = this.facialMessage.postInitializeRemoteEnroll(device);
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				user.setFoto(Base64.getDecoder().decode(new JSONObject(response.body()).get("user_image").toString()));
				user.setImageTimestamp(Instant.now().getEpochSecond());
				// armazenar usuário no banco de dados e armazenar menagens de cadastro
				this.executeDataStore(user);
				//executar broadcast
				this.doBroadcast();
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	public Boolean doBroadcast() {
		boolean retorno = false;
		List<Device> listDevicesBroadcast = new ArrayList<Device>();
		List<User> listUsuariosBroadcast = new ArrayList<User>();
		Optional<Device> optDevice;
		Device device = null;
		Optional<User> optUsuario;
		User usuario = null;
		
		
		for (MessageBroadcast messageBroadcast : this.messageBroadcastRepository.findAllByOrderByIdAsc()) {
			
			// recuperar device
			// recuperar o device na lista 
			optDevice = listDevicesBroadcast.stream().filter(dev -> dev.getId().equals(messageBroadcast.getDeviceId())).findFirst();
			// caso nao esteja na lista, carregar do BD
			if (!optDevice.isPresent()) {
				optDevice = deviceRepository.findById(messageBroadcast.getDeviceId());
				if (!optDevice.isPresent()) {
					// device cadastrado anteriormente foi descadastrado do banco de dados
					// TODO lançar exceção de forma consistente
					throw new MessageBroadcastException("device cadastrado anteriormente foi descadastrado do banco de dados");
				} else {
					device = optDevice.get();
					// verifica se o token está valido
					if (!device.checkIfSessionTokenIsValid()) {
						device.renewSessionToken();
						deviceRepository.save(device);
					}
					listDevicesBroadcast.add(device);
				}
			} else {
				device = optDevice.get();
			}
			
			// recuperar usuário
			// recuperar o usuário na lista 
			optUsuario = listUsuariosBroadcast.stream().filter(user -> user.getUserId().equals(messageBroadcast.getUserId())).findFirst();
			// caso nao esteja na lista, carregar do BD
			if (!optUsuario.isPresent()) {
				optUsuario = userRepository.findById(messageBroadcast.getUserId());
				if (!optUsuario.isPresent()) {
					// usuário cadastrado anteriormente foi descadastrado do banco de dados
					// TODO lançar exceção de forma consistente
					throw new MessageBroadcastException("usuário cadastrado anteriormente foi descadastrado do banco de dados");
				} else {
					usuario = optUsuario.get();
					listUsuariosBroadcast.add(optUsuario.get());
				}
			} else {
				usuario = optUsuario.get();
			}		
			
			// 1- criar usuario no device
			if (messageBroadcast.getTipo().equalsIgnoreCase(MessageBroadcastTypeEnum.criacao_de_usuario.name())) {
				if (this.cadastroUsuarioService.criarUsuario(device,Arrays.asList(usuario)) != null) {
					// remover registro da tabela de broadcast
					this.excluirMessageBroadcast(messageBroadcast);
				} else {
					retorno = false;
					// TODO lançar exceção de forma consistente
					//throw new MessageBroadcastException("Erro ao enviar mensagem de criação de vinculação entre usuário e grupos"); 
				}
			}
			
			// 2 - criar a vinculacao de usuario e grupo no device
			if (messageBroadcast.getTipo().equalsIgnoreCase(MessageBroadcastTypeEnum.criacao_de_vinculacao_de_usuario_com_grupo.name())) {
				if (this.cadastroUsuarioService.vincularUsuarioGrupo(device,Arrays.asList(usuario)) != null) {
					// remover registro da tabela de broadcast
					this.excluirMessageBroadcast(messageBroadcast);
				} else {
					retorno = false;
					// TODO lançar exceção de forma consistente
					//throw new MessageBroadcastException("Erro ao enviar mensagem de criação de vinculação entre usuário e grupos"); 
				}
			}
			
			// 3 - criar a imagem do usuario no device
			if (messageBroadcast.getTipo().equalsIgnoreCase(MessageBroadcastTypeEnum.criacao_de_imagem.name())) {
				if (this.cadastroUsuarioService.persistFoto(device,usuario) != null) {
					// remover registro da tabela de broadcast
					this.excluirMessageBroadcast(messageBroadcast);
				} else {
					retorno = false;
					// TODO lançar exceção de forma consistente
					//throw new MessageBroadcastException("Erro ao enviar mensagem de criação de vinculação entre usuário e grupos"); 
				}
				
			}
			
		}
		
		return retorno;
		
	}
	
	@Transactional
	private void executeDataStore (User user) throws URISyntaxException {
		
		// salvar o novo usuario cadastrado no dispositivo no banco de dados
		userRepository.save(user);
		
		// recupera todos os devices cadastrados (aqueles que já se cadastraram através dos pushes
		List<Device> listDevices = deviceRepository.findAll();
					
		// sao geradas e armazenadas as mensagens que deverao ser enviadas para cada device
		MessageBroadcast msg;
		for (Device device : listDevices) {
			
			Long timestampOperation = new Timestamp(new Date().getTime()).getTime();
			
			//Criação de usuario no device
			msg = new MessageBroadcast(
					device.getId(),
					user.getUserId(),
					MessageBroadcastTypeEnum.criacao_de_usuario.name(),
					timestampOperation);
			
			messageBroadcastRepository.save(msg);
			
			
			//criacao_de_vinculacao_de_usuario_com_grupo
			msg = new MessageBroadcast(
					device.getId(),
					user.getUserId(),
					MessageBroadcastTypeEnum.criacao_de_vinculacao_de_usuario_com_grupo.name(),
					timestampOperation);
			
			messageBroadcastRepository.save(msg);

			//criacao de imagem no device
			msg = new MessageBroadcast(
					device.getId(),
					user.getUserId(),
					MessageBroadcastTypeEnum.criacao_de_imagem.name(),
					timestampOperation);
			
			messageBroadcastRepository.save(msg);
		}
		
	}
	
	@Transactional
	private void excluirMessageBroadcast(MessageBroadcast messageBroadcast) {
		// remover registro da tabela de broadcast
		messageBroadcastRepository.delete(messageBroadcast);
	}
		
}
