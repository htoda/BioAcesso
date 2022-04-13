package br.com.bioconnect.BioAcesso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class Configuracoes {

	public static void main(String[] args) {
		String session = "VgKwWzQweYk05qDiNmopunl3";
		HttpRequest request = null;
		String sURL = "http://192.168.0.129/set_configuration.fcgi?session=" + session;
		String body = "{\n"
					+ "	\"push_server\": {\n"
					+ "    \"push_remote_address\": \"192.168.0.18:8080\",\n"
					+ "    \"push_request_timeout\": \"3\",\n"
					+ "    \"push_request_period\": \"10\"\n"
					+   "}\n"
					+ "}";
		System.out.println(body);
		

		try {
			request = HttpRequest.newBuilder().uri(new URI(sURL))
					.headers("Content-Type", "application/json")
					 .timeout(Duration.ofSeconds(3))
					.POST(HttpRequest.BodyPublishers.ofString(body)).build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		try {
			HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			System.out.println(response.body());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}
}
