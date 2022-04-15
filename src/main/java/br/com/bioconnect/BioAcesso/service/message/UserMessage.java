package br.com.bioconnect.BioAcesso.service.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;
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
public class UserMessage {
	
	//Cadastrar usuário(s)
	public HttpRequest createUserMessage (Device device, List<User> listUsuario) throws URISyntaxException {
		String URLContext = "/create_objects.fcgi?session=" + device.getSessionTokenTreated();
		String body = this.createUserMessageBody(device, listUsuario);
		
		return this.postRequestBuilder(device,body,URLContext);
	}
	
	public String createUserMessageURI (Device device) {
		String URLContext = "/create_objects.fcgi?session="; 
		return ConfigEnums.defaultHttpProtocol.valor + device.getIp() + ":" + device.getPort() + URLContext;
	}
	
	public String createUserMessageBody (Device device, List<User> listUsuario) throws URISyntaxException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode message = mapper.createObjectNode();
		ArrayNode bodyArray = mapper.createArrayNode();
		ObjectNode bodyUserImage;
		
		// criar elementos do array de user
		for (User usuario : listUsuario) {
			bodyUserImage = mapper.createObjectNode();
			bodyUserImage.put("id", usuario.getUserId());
			bodyUserImage.put("name", usuario.getName());
			bodyUserImage.put("registration", usuario.getRegistration());
			bodyUserImage.put("password", usuario.getPassword());
			bodyUserImage.put("salt", usuario.getSalt());
			bodyUserImage.put("expires", usuario.getExpires());
			bodyUserImage.put("user_type_id", usuario.getUserId());
			bodyUserImage.put("begin_time", usuario.getBeginTime());
			bodyUserImage.put("end_time", usuario.getEndTime());
			bodyUserImage.put("image_timestamp", usuario.getImageTimestamp());
			
			bodyArray.add(bodyUserImage);
		}
		
		// adicionando body array no nó user image
		message.put("object", "users");
		message.set("values", bodyArray);
		
		//String body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
		String body;
		try {
			body = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JSONException("Problema na estruturação de JSON");
		}
		
		return body;
	}
	
	public HttpRequest createUserGroupMessage(Device device, List<User> listUsuario) throws URISyntaxException {
		String URLContext = "/create_objects.fcgi?session=" + device.getSessionTokenTreated();
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode message = mapper.createObjectNode();
		ArrayNode bodyArray1 = mapper.createArrayNode();
		ObjectNode bodyUserValues;
		
		// criar elementos do array de user e group
		for (User usuario : listUsuario) {
			if (!usuario.getListGroups().isEmpty()) {
				bodyUserValues = mapper.createObjectNode();
				bodyUserValues.put("user_id", usuario.getUserId());
				bodyUserValues.put("group_id", usuario.getListGroups().iterator().next().getGroupId());
				bodyArray1.add(bodyUserValues);
			}
		}
	
		// raiz
		message.put("object", "user_groups");
		message.put("fields", "[\"user_id\",\"group_id\"]");
		// adicionando body array fields no nó raiz
		message.set("values", bodyArray1);
		
		//String body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
		String body;
		try {
			body = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JSONException("Problema na estruturação de JSON");
		}
		
		return this.postRequestBuilder(device,body,URLContext);
	}
	
	public HttpRequest createUserRoleMessage(Device device, List<User> listUsuario) throws URISyntaxException {
		String URLContext = "/create_objects.fcgi?session=" + device.getSessionTokenTreated();
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode message = mapper.createObjectNode();
		ArrayNode bodyArray1 = mapper.createArrayNode();
		ObjectNode bodyUserValues;
		
		// criar elementos do array de user e group
		for (User usuario : listUsuario) {
			if (usuario.getListRoles()!= null && !usuario.getListRoles().isEmpty()) {
				bodyUserValues = mapper.createObjectNode();
				bodyUserValues.put("user_id", usuario.getUserId());
				bodyUserValues.put("role", usuario.getListRoles().iterator().next().getRoleId());
				bodyArray1.add(bodyUserValues);
			}
		}
		
		if (bodyArray1.size() == 0) return null;
	
		// raiz
		message.put("object", "user_roles");
		message.put("fields", "[\"user_id\",\"role\"]");
		// adicionando body array fields no nó raiz
		message.set("values", bodyArray1);
		
		//String body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
		String body;
		try {
			body = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JSONException("Problema na estruturação de JSON");
		}
		
		return this.postRequestBuilder(device,body,URLContext);
	}
	
	
	public HttpRequest deleteUserMessage (Device device, List<User> listUsuario) throws URISyntaxException {
		String URLContext = "/destroy_objects.fcgi?session=" + device.getSessionTokenTreated();
		
		// TODO - lista
		String listaIds = null;
		for (User usuario : listUsuario) {
			listaIds = usuario.getUserId().toString();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode message = mapper.createObjectNode();
		message.put("object", "users");
		ObjectNode messageWhere = mapper.createObjectNode();
		message.set("where", messageWhere);
		ObjectNode messageUsers = mapper.createObjectNode();
		messageWhere.set("users",messageUsers);
		ObjectNode messageId = mapper.createObjectNode();
		messageUsers.put("id",listaIds);
		
		//String body = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message);
		String body;
		try {
			body = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new JSONException("Problema na estruturação de JSON");
		}
			
		return this.postRequestBuilder(device,body,URLContext);
	}
	
	private HttpRequest postRequestBuilder (Device device, String body, String URLContext) throws URISyntaxException {
		return HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + device.getIp() + ":" + device.getPort() + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
	}

}
