package br.com.bioconnect.BioAcesso;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Session {

	public static void main(String[] args) {
		HttpRequest request = null;
		String sURL = "http://192.168.0.129/login.fcgi";
		String body = "{\n"
					+ "    \"login\": \"admin\",\n"
					+ "    \"password\": \"admin\"\n"
					+ "}";
		
		try {
			request = HttpRequest.newBuilder().uri(new URI(sURL))
					.headers("Content-Type", "application/json;charset=UTF-8")
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
