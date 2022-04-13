package br.com.bioconnect.BioAcesso.service.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.List;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.User;
import br.com.bioconnect.BioAcesso.util.ConfigEnums;

@Service
public class FacialMessage {

	//Obter lista de usuários com foto cadastrada
	public HttpRequest getListImagesFromDevices(Device device) throws URISyntaxException {
		String URLContext = "/user_list_images.fcgi?get_timestamp=1&session=" + device.getSessionTokenTreated();;
		return this.getRequestBuilder(device,URLContext);
	}
	
	//Obter imagem de usuário
	public HttpRequest getImageFromDevice(Device device, User usuario) throws URISyntaxException {
		String URLContext = "/user_get_image.fcgi?user_id=" + usuario.getUserId() + "&session=" + device.getSessionTokenTreated();;
		return this.getRequestBuilder(device,URLContext);
	}
	
	//Obter lista de fotos de usuário
	public HttpRequest postDetailedPhotoListFromDevice(Device device, List<String> userIds) throws URISyntaxException {
		String URLContext = "/user_get_image_list.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "	\"user_ids\": [\n" 
					+ userIds.toString()
					+ "]\n" 
					+ "}";
		System.out.println(body);

		return this.postRequestBuilder(device,body,URLContext);
	}

	//Cadastrar foto individual de usuário
	public HttpRequest setPhotoToDevice(Device device, User usuario) throws URISyntaxException {
		String URLContext = "/user_set_image.fcgi?" 
						+ "user_id=" + usuario.getUserId() 
						+ "&timestamp=" + usuario.getImageTimestamp()
						+  "&match=1" // o cadastro da foto deverá ser rejeitado se o rosto já estiver cadastrado para outro usuário
						+ "&session=" + device.getSessionTokenTreated();
		
		return this.postRequestBuilderOctetStream(device,usuario.getFoto(),URLContext);
	}	
	
	//Cadastrar lista de fotos de usuário
	public HttpRequest setPhotoListToDevice(Device device, List<User> listUsuario, boolean matchEnabled) throws URISyntaxException {
		String URLContext = "/user_set_image_list.fcgi?session=" + device.getSessionTokenTreated();

		
		ObjectMapper mapper = new ObjectMapper();
		// criar estrutura base
		ObjectNode message = mapper.createObjectNode();
		ArrayNode bodyArray = mapper.createArrayNode();
		ObjectNode bodyUserImage;
		
		// criar elementos do array de user image
		for (User usuario : listUsuario) {
			bodyUserImage = mapper.createObjectNode();
			bodyUserImage.put("user_id", usuario.getUserId());
			bodyUserImage.put("timestamp", usuario.getImageTimestamp());
			bodyUserImage.put("image", Base64.getEncoder().encodeToString(usuario.getFoto()));
			bodyArray.add(bodyUserImage);
		}
		
		// adicionando body array no nó user image
		
		
		message.put("match", matchEnabled);
		message.set("user_images", bodyArray);
		String body;
		try {
			body = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JSONException("Problema na estruturação de JSON");
		}

		return this.postRequestBuilder(device,body,URLContext);
	}	
	
	public HttpRequest postInitializeRemoteEnroll (Device device) throws URISyntaxException {
		String URLContext = "/remote_enroll.fcgi?session=" + device.getSessionTokenTreated();
				
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode message = mapper.createObjectNode();
		message.put("type", "face");
		//message.put("user_id", usuario.getUserId());
		message.put("save",false);
		message.put("sync",true);
		message.put("auto",true);
		message.put("countdown",2);
		
		//String body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
		String body;
		try {
			body = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JSONException("Problema na estruturação de JSON");
		}
			
		return this.postRequestBuilderEnroll(device,body,URLContext);
	}
	
	public HttpRequest postCancelRemoteEnroll (Device device) throws URISyntaxException {
		String URLContext = "/cancel_remote_enroll.fcgi?session=" + device.getSessionTokenTreated();
		String body ="";
			
		return this.postRequestBuilder(device,body,URLContext);
	}
	
	public HttpRequest postShowStatelRemoteEnroll (Device device) throws URISyntaxException {
		String URLContext = "/enroller_state.fcgi?session=" + device.getSessionTokenTreated();
		String body ="";
		
		return this.postRequestBuilder(device,body,URLContext);
	}
	
	
	private HttpRequest getRequestBuilder (Device device, String URLContext) throws URISyntaxException {
		return HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + device.getIp() + ":" + device.getPort() + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.GET()
				.build();
	}
	
	private HttpRequest postRequestBuilder (Device device, String body, String URLContext) throws URISyntaxException {
		return HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + device.getIp() + ":" + device.getPort() + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
	}

	private HttpRequest postRequestBuilderEnroll (Device device, String body, String URLContext) throws URISyntaxException {
		return HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + device.getIp() + ":" + device.getPort() + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials_Enroll.valor)))
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
	}
	
	private HttpRequest postRequestBuilderOctetStream (Device device, byte[] body, String URLContext) throws URISyntaxException {
		return HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + device.getIp() + ":" + device.getPort() + URLContext))
				.headers("Content-Type", "application/octet-stream")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.POST(HttpRequest.BodyPublishers.ofByteArray(body))
				.build();
	}
	
}
