package br.com.bioconnect.BioAcesso.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.User;
import br.com.bioconnect.BioAcesso.model.dto.UserDeviceDto;
import br.com.bioconnect.BioAcesso.service.message.ConfigMessage;
import br.com.bioconnect.BioAcesso.service.message.FacialMessage;
import br.com.bioconnect.BioAcesso.service.message.UserMessage;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UserMessage userMessage;
	
	@Autowired
	private FacialMessage facialMessage;
	
	@Autowired
	private ConfigMessage configMessage;

	public String criarUsuario(Device device, List<User> listUsuario) {
		HttpRequest req;
		HttpResponse<String> response;

		try {
			req = userMessage.createUserMessage(device,listUsuario);
			// req = postRequestBuilderMsgType1(messageBroadcast.getURLMessage() +
			// device.getSessionTokenTreated(), messageBroadcast.getBodyMessage());
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				return null;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String vincularUsuarioGrupo(Device device, List<User> listUsuario) {
		HttpRequest req;
		HttpResponse<String> response;

		try {
			req = this.userMessage.createUserGroupMessage(device,listUsuario);
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				return null;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String persistFoto(Device device, User usuario) {
		HttpRequest req;
		HttpResponse<String> response;

		try {
			// cadastrar foto do usuario
			req = this.facialMessage.setPhotoToDevice(device, usuario);
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				return null;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String persistFotos(Device device, List<User> listUsuario, boolean matchEnabled) {
		HttpRequest req;
		HttpResponse<String> response;

		try {
			// cadastrar foto do usuario
			req = this.facialMessage.setPhotoListToDevice(device, listUsuario, matchEnabled);
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				return null;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String vincularUsuarioRole(Device device, List<User> listUsuario) {
		HttpRequest req;
		HttpResponse<String> response;

		try {
			req = this.userMessage.createUserRoleMessage(device,listUsuario);
			if (req == null) return "Sem administradores a cadastrar";
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				return null;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public  List<User> listarUsuarioFromDevice(Device device) {
		HttpRequest req;
		HttpResponse<String> response;

		try {
			req = configMessage.getLoadObject(device,"users");
			// req = postRequestBuilderMsgType1(messageBroadcast.getURLMessage() +
			// device.getSessionTokenTreated(), messageBroadcast.getBodyMessage());
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				// instancia um novo JSONObject passando a string como entrada
				JSONObject json_obj = new JSONObject(response.body());

				// recupera o array "image_info"
				JSONArray image_info_array = json_obj.getJSONArray("users");
				ObjectMapper objectMapper = new ObjectMapper();
				
				List<UserDeviceDto> listUserDeviceDto = objectMapper.readValue(image_info_array.toString(),new TypeReference<List<UserDeviceDto>>(){});
				
				return listUserDeviceDto.stream().map(l -> new User(l)).collect(Collectors.toList());
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
