package br.com.bioconnect.BioAcesso.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.bioconnect.BioAcesso.Exception.ErroEnvioMensagemException;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.dtodevice.NewUserIdentifiedDTO;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;
import br.com.bioconnect.BioAcesso.service.message.AuthorizationMessage;
import br.com.bioconnect.BioAcesso.util.EventEnum;

@Service
public class AuthorizationService {

	@Autowired
	private AuthorizationMessage authorizationMessage;

	@Autowired
	private IDeviceRepository deviceRepository;

	public String doAuthorization(NewUserIdentifiedDTO userWaitingAuthorization, Device device) throws URISyntaxException {
		String userIdToAnalise = userWaitingAuthorization.getUser_id();
		Boolean liberarEntrada = false;

		// logica de avaliacão cadastral do aluno
		if (true) {
			System.out.println("Usuário " + userIdToAnalise + " autorizado");
			liberarEntrada = true;
		}
		
		// enviar mensagem de autorizacao para ser exibido nos devices
		this.sendAuthorizationMessage(userIdToAnalise, liberarEntrada, device);
		
		return this.createReturnMessage(userWaitingAuthorization, liberarEntrada);

	}

	private void sendAuthorizationMessage(String usuario, Boolean liberarEntrada,Device device) {
		String displayMessage;
		
		// recuperar device para envio de mensagem
		Optional<Device> optDevice = this.deviceRepository.findByIp(device.getIp());
		if (optDevice.isPresent())
			device = optDevice.get();
		if (liberarEntrada) {
			displayMessage = "Usuário(a) " + usuario + " autorizado(a)";
		} else {
			displayMessage = "Usuário(a) " + usuario + " não autorizado(a)";
		}

		try {
			// setar o servidor online
			HttpRequest req = this.authorizationMessage.getAuthorizationMessageToShowInDevice(device, displayMessage);
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() != 200) {
				throw new ErroEnvioMensagemException(
						"Erro ao enviar mensagem de resultado de autorização para o device");
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}

	}

	private String createReturnMessage(NewUserIdentifiedDTO userWaitingAuthorization, Boolean liberarEntrada) {

		/*
		 * { "result": { "event": 7, "user_id": 6, "user_name": "Neal Caffrey",
		 * "user_image": false, "portal_id": 1, "actions":[ {"action": "door",
		 * "parameters": "door=1"}, {"action": "door", "parameters": "door=2"} ] } }
		 */

		// criar retorno
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode result = mapper.createObjectNode();

		// criar JSON filho (bodyN1)
		ObjectNode bodyN1 = mapper.createObjectNode();
		if (liberarEntrada) {
			bodyN1.put("event", EventEnum.acesso_autorizado.valor);
		} else {
			bodyN1.put("event", EventEnum.acesso_negado.valor);
		}
		bodyN1.put("user_id", Integer.valueOf(userWaitingAuthorization.getUser_id()));
		bodyN1.put("user_name", userWaitingAuthorization.getUser_name());
		bodyN1.put("user_image", (userWaitingAuthorization.getUser_has_image() == "0" ? false : true));
		bodyN1.put("portal_id", Integer.valueOf(userWaitingAuthorization.getPortalId()));

		// criar JSON filho (bodyN2)
		ObjectNode bodyN2EL1 = mapper.createObjectNode();
		bodyN2EL1.put("action", "sec_box");
		bodyN2EL1.put("parameters", "id=65793, reason=1");

		ArrayNode bodyN2 = mapper.createArrayNode();
		bodyN2.addAll(Arrays.asList(bodyN2EL1));

		// adicionando bodyN2 em bodyN1
		bodyN1.set("actions", bodyN2);

		// adicionando bodyN1 em result
		result.set("result", bodyN1);

		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;

	}

}