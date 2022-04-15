package br.com.bioconnect.BioAcesso.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.User;
import br.com.bioconnect.BioAcesso.model.dtodevice.UserDeviceDto;
import br.com.bioconnect.BioAcesso.repository.IUserRepository;
import br.com.bioconnect.BioAcesso.service.message.FacialMessage;

@Service
public class PhotoSyncronizationService {

	@Autowired
	private FacialMessage facialMessage;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private ConfigService configService;

	public String doPhotoSyncronization(Device device) {
		JSONObject json_obj;
		JSONArray json_array;
		ObjectMapper objectMapper;
		StringBuilder sb = new StringBuilder();
		String retorno = null;
		
		sb.append("Iniciando processo de sincronização de usuários ... \n");

		// Recuperar lista de usuários cadastrados no do banco de dados
		List<User> listaUsuarioBD = this.userRepository.findAll();
		if (listaUsuarioBD.size() == 0) {
			return "Não foram encontrados usuários no Banco de dados";
		} else {
			sb.append("Quantidade de usuários no banco de dados: " + listaUsuarioBD.size() + "\n");	
		}
		
		// Recuperar lista de usuários cadastrados no do banco de dados
		//List<User> listaUsuarioBDComFoto = this.userRepository.findByFotoIsNotNull();
		//sb.append("Quantidade de usuários com foto no banco de dados: " + listaUsuarioBDComFoto.size() + "\n");	

		// Recuperar lista de usuários cadastrados no device
		List<UserDeviceDto> listUsuarioDeviceDTO = null;
		json_obj = new JSONObject(this.configService.getLoadObject(device, "users"));
		// recupera o array "image_info"
		json_array = json_obj.getJSONArray("users");
		objectMapper = new ObjectMapper();
		try {
			listUsuarioDeviceDTO = objectMapper.readValue(json_array.toString(),
					new TypeReference<List<UserDeviceDto>>() {
					});
			
			sb.append("Quantidade de usuários device: " + listUsuarioDeviceDTO.size() + "\n");	

		} catch (JsonProcessingException e2) {
			e2.printStackTrace();
		}
		
		List<User> listaUsuarioDevice = listUsuarioDeviceDTO.stream()
				.map(userDTO -> new User(userDTO.getId(), userDTO.getImage_timestamp()))
				.collect(Collectors.toList());
		
		/*
		
		// Recuperar lista de usuários cadastrados com fotos do device
		List<UserPhotoTimestampDTO> listUsuarioDeviceComFotoDTO = null;
		try {
			HttpRequest req = this.facialMessage.getListImagesFromDevices(device);
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());

			// instancia um novo JSONObject passando a string como entrada
			json_obj = new JSONObject(response.body());

			// recupera o array "image_info"
			json_array = json_obj.getJSONArray("image_info");
			objectMapper = new ObjectMapper();

			listUsuarioDeviceComFotoDTO = objectMapper.readValue(json_array.toString(),
					new TypeReference<List<UserPhotoTimestampDTO>>() {
					});

			sb.append("Quantidade de usuários com foto no device: " + listUsuarioDeviceComFotoDTO.size() + "\n");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<User> listaUsuarioDeviceComFoto = listUsuarioDeviceComFotoDTO.stream()
				.map(userDTO -> new User(userDTO.getUser_id(), userDTO.getTimestamp()))
				.collect(Collectors.toList());

		*/
		

		// Identificar cadastros de usuários existentes apenas no banco de dados ->
		// cadastrar usuários e imagem no device
		List<User> listUsuariosCadastrarNoDevice = listaUsuarioBD.stream()
				.filter(element -> !listaUsuarioDevice.contains(element)).collect(Collectors.toList());

		if (listUsuariosCadastrarNoDevice.size() > 0) {

			// enviar msgs de cadastro de usuarios
			retorno = this.cadastroUsuarioService.criarUsuario(device, listUsuariosCadastrarNoDevice);
			if (retorno != null) {
				System.out.println(retorno);
			} else {
				return "Erro no processo de excução de criação de usuário - criarUsuario";
			}
		
			// enviar msgs de cadastro de vinculo de usuarios e grupo
			retorno = this.cadastroUsuarioService.vincularUsuarioGrupo(device, listUsuariosCadastrarNoDevice);
			if (retorno != null) {
				System.out.println(retorno);
			} else {
				return "Erro no processo de excução de criação de usuário - vincularUsuarioGrupo";
			}
		
			// enviar msgs de cadastro de vinculo de usuarios e role
			// se usuário for administrador deverá ser incluído vínculo
			retorno = this.cadastroUsuarioService.vincularUsuarioRole(device, listUsuariosCadastrarNoDevice);
			if (retorno != null) {
				System.out.println(retorno);
			} else {
				return "Erro no processo de excução de criação de usuário - vincularUsuarioRole";
			}
			
			retorno = this.cadastroUsuarioService.persistFotos(device, listUsuariosCadastrarNoDevice, true);
			if (retorno != null) {
				//System.out.println(retorno);
			} else {
				return "Erro no processo de excução de criação de usuário - criarFotos";
			}

		}

		sb.append("Quantidade de usuários cadastrados no device: " + listUsuariosCadastrarNoDevice.size() + "\n");
		//sb.append("ids de usuários cadastrados no device: " + listUsuariosCadastrarNoDevice.toString() + "\n");

		
		/*
		// Identificar cadastros de usuários com foto existentes apenas no banco de dados -> cadastrar imagem no device
		List<User> listUsuariosCadastrarFotoNoDevice = listaUsuarioBDComFoto.stream()
				.filter(element -> !listaUsuarioDeviceComFoto.contains(element)).collect(Collectors.toList());
		
		// enviar msgs de cadastramento de imagens para o device
		if (listUsuariosCadastrarFotoNoDevice.size() > 0) {
			retorno = this.cadastroUsuarioService.persistFotos(device, listUsuariosCadastrarFotoNoDevice, true);
			if (retorno != null) {
				//System.out.println(retorno);
			} else {
				return "Erro no processo de excução de criação de usuário - criarFotos";
			}
		}
		sb.append("Quantidade de fotos usuários cadastrados no device: " + listUsuariosCadastrarFotoNoDevice.size() + "\n");
		*/
		

		// Identificar cadastros de usuários com foto existentes no BD e no device, comparar datas e atualizar no device caso a foto do BD esteja mais atualizada		
		List<User> listaUsuariosAtualizarDevice = listaUsuarioBD.stream()
				.filter(element -> element.getImageTimestamp() != null)  // filtra usuários do BD que tem foto
				.filter(element -> listaUsuarioDevice.contains(element)) // filtra usuários do BD que estão cadastrados no device 
				.filter(element -> element.getImageTimestamp().compareTo(// filtra usuários do BD cuja foto tenha timestamp superior (foto mais recente) a foto do device 
						listaUsuarioDevice.get(listaUsuarioDevice.indexOf(element)).getImageTimestamp()) > 0)
				.collect(Collectors.toList());

		// enviar msgs de atualizacao de imagens para o device
		if (listaUsuariosAtualizarDevice.size() > 0) {
			retorno = this.cadastroUsuarioService.persistFotos(device, listaUsuariosAtualizarDevice, true);
			if (retorno == null) {
				return "Erro no processo de excução de atualização fotos do usuário - criarFotos";
			}
		}
		
		sb.append("Quantidade de usuários com fotos atualizadas no device: " + listaUsuariosAtualizarDevice.size() + "\n");
		//sb.append("Identificadores de usuários com todos atualizadas no device: " + retorno + "\n");

		return sb.toString();

	}

	public String doFullPhotoLoadInDevice(Device device) {
		StringBuilder sb = new StringBuilder();
		sb.append("Iniciando processo de carga ... \n");
		
		// Recuperar lista de usuários cadastrados no do banco de dados
		List<User> listaUsuarioBD = this.userRepository.findAll();
		sb.append("Quantidade de usuários no banco de dados: " + listaUsuarioBD.size() + "\n");
		
		if (listaUsuarioBD.size() == 0)
			return "Não foram encontrados usuários no Banco de dados";

		// enviar msgs de cadastro de usuarios e imagens para o device
		String retorno = this.cadastroUsuarioService.criarUsuario(device, listaUsuarioBD);
		if (retorno != null) {
			System.out.println(retorno);
		} else {
			return "Erro no processo de excução de criação de usuário - criarUsuario";
		}

		retorno = this.cadastroUsuarioService.vincularUsuarioGrupo(device, listaUsuarioBD);
		if (retorno != null) {
			System.out.println(retorno);
		} else {
			return "Erro no processo de excução de criação de usuário - vincularUsuarioGrupo";
		}

		// se usuário for administrador deverá ser incluído vínculo
		retorno = this.cadastroUsuarioService.vincularUsuarioRole(device, listaUsuarioBD);
		if (retorno != null) {
			System.out.println(retorno);
		} else {
			return "Erro no processo de excução de criação de usuário - vincularUsuarioRole";
		}
		
		retorno = this.cadastroUsuarioService.persistFotos(device, listaUsuarioBD, false);
		if (retorno != null) {
			System.out.println(retorno);
		} else {
			return "Erro no processo de excução de criação de usuário - criarFotos";
		}
		
		sb.append("Quantidade cadastrados no device: " + listaUsuarioBD.size() + "\n");
		//sb.append("ids de usuários cadastrados no device: " + listaUsuarioBD.toString() + "\n");

		return sb.toString();

	}

	@Transactional
	public void copyAndPersistUserAndPhotosFromDevice(Device device) {
		List<User> listaUsuarios = this.cadastroUsuarioService.listarUsuarioFromDevice(device);
		for (User user : listaUsuarios) {
			if (recuperarFoto(device, user) != null)
				user.setFoto(recuperarFoto(device, user));
			this.userRepository.save(user);
		}
	}

	private byte[] recuperarFoto(Device device, User user) {
		try {
			HttpRequest req = this.facialMessage.getImageFromDevice(device, user);
			HttpResponse<byte[]> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofByteArray());
			if (response.statusCode() == 200) {
				return response.body();
			}
		} catch (URISyntaxException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
