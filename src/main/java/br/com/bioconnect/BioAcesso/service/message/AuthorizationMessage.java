package br.com.bioconnect.BioAcesso.service.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;

import org.springframework.stereotype.Service;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.util.ConfigEnums;

@Service
public class AuthorizationMessage {
	
	public HttpRequest getAuthorizationMessageToShowInDevice(Device device, String msg) throws URISyntaxException {
		int timeout = 12000; // tempo de exibicao da mensagem de autorizacao no device
		
		String URLContext = "/message_to_screen.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "    \"message\": \"" + msg + "\",\n"
					+ "    \"timeout\":"  + timeout + "\n" 
					+ "}";

		return this.postRequestBuilder(device, body, URLContext);
	}
	
	private HttpRequest postRequestBuilder(Device device, String body, String URLContext) throws URISyntaxException {
		return HttpRequest.newBuilder()
				.uri(new URI(
						ConfigEnums.defaultHttpProtocol.valor + device.getIp() + ":" + device.getPort() + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.POST(HttpRequest.BodyPublishers.ofString(body)).build();
	}

}
