package br.com.bioconnect.BioAcesso;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.json.JSONObject;

import br.com.bioconnect.BioAcesso.util.ConfigEnums;

public class TestCheckSession {

	public static void main(String[] args) {
		HttpRequest request;
		HttpResponse<String> response;
		String URLContext = "/session_is_valid.fcgi?session=" + "dwdwd";
		try {
			request = HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + "192.168.0.129" + ":" + 80 + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.POST(HttpRequest.BodyPublishers.ofString("")).build();
			response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			System.out.println(response.body());
			JSONObject respJson = new JSONObject(response.body());
			System.out.println(respJson.getBoolean("session_is_valid"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

}
