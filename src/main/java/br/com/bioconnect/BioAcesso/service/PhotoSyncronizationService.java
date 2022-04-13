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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.User;
import br.com.bioconnect.BioAcesso.model.dtodevice.UserPhotoTimestampDTO;
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

	public String doPhotoSyncronization(Device device) {
		StringBuilder sb = new StringBuilder();
		String retorno;
		
		sb.append("Iniciando processo de carga ... \n");

		// Recuperar lista de usuários cadastrados no do banco de dados
		List<User> listaUsuarioBD = this.userRepository.findAll();
		sb.append("Quantidade de usuários no banco de dados: " + listaUsuarioBD.size() + "\n");
		if (listaUsuarioBD.size() == 0)
			return "Não foram encontrados usuários no Banco de dados";

		// Recuperar lista de usuários cadastrados com fotos do device
		JSONObject json_obj = null;
		List<UserPhotoTimestampDTO> listUsuarioDeviceDTO = null;

		try {
			HttpRequest req = this.facialMessage.getListImagesFromDevices(device);
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());

			// instancia um novo JSONObject passando a string como entrada
			json_obj = new JSONObject(response.body());

			// recupera o array "image_info"
			JSONArray image_info_array = json_obj.getJSONArray("image_info");
			ObjectMapper objectMapper = new ObjectMapper();

			listUsuarioDeviceDTO = objectMapper.readValue(image_info_array.toString(),
					new TypeReference<List<UserPhotoTimestampDTO>>() {
					});

			sb.append("Quantidade de usuários no device: " + listUsuarioDeviceDTO.size() + "\n");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		List<User> listaUsuarioDevice = listUsuarioDeviceDTO.stream()
				.map(userDTO -> new User(userDTO.getUser_id(), userDTO.getTimestamp()))
				.collect(Collectors.toList());

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
				System.out.println(retorno);
			} else {
				return "Erro no processo de excução de criação de usuário - criarFotos";
			}

		}

		sb.append("Quantidade cadastrados no device: " + listUsuariosCadastrarNoDevice.size() + "\n");
		//sb.append("ids de usuários cadastrados no device: " + listUsuariosCadastrarNoDevice.toString() + "\n");

		// Identificar fotos mais atualizadas no banco de dados -> atualizar imagem no
		// device
		/*
		 * List<UserPhotoTimestampDTO> listaElementosExistentesAmbos =
		 * listUserPhotoTimestampDTOFromBD .stream() .filter(element ->
		 * listUsuarioDeviceFlatList.contains(element)) .collect(Collectors.toList());
		 * 
		 * List<UserPhotoTimestampDTO> listaElementosAtualizarDevice =
		 * listaElementosExistentesAmbos .stream() .filter(element ->
		 * element.getPhotoTimestamp().compareTo(listUsuarioDeviceFlatList.get(
		 * listUsuarioDeviceFlatList.indexOf(element)).getPhotoTimestamp())> 0)
		 * .collect(Collectors.toList());
		 */
		
		List<User> listaUsuariosAtualizarDevice = listaUsuarioBD.stream()
				.filter(element -> listaUsuarioDevice.contains(element))
				.filter(element -> element.getImageTimestamp().compareTo(
						listaUsuarioDevice.get(listaUsuarioDevice.indexOf(element)).getImageTimestamp()) > 0)
				.collect(Collectors.toList());

		// enviar msgs de atualizacao de imagens para o device
		if (listaUsuariosAtualizarDevice.size() > 0) {
			retorno = this.cadastroUsuarioService.persistFotos(device, listaUsuariosAtualizarDevice, true);
			if (retorno != null) {
				System.out.println(retorno);
			} else {
				return "Erro no processo de excução de atualização fotos do usuário - criarFotos";
			}
		}
		
		sb.append("Quantidade de usuários com todos atualizadas no device: " + listaUsuariosAtualizarDevice.size() + "\n");
		//sb.append("ids de usuários com todos atualizadas no device: " + listaUsuariosAtualizarDevice.toString() + "\n");

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
