package br.com.bioconnect.BioAcesso.model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.json.JSONObject;

import br.com.bioconnect.BioAcesso.model.form.DeviceForm;
import br.com.bioconnect.BioAcesso.model.form.DeviceUpdateForm;
import br.com.bioconnect.BioAcesso.util.ConfigEnums;

@Entity
public class Device {

	@Id
	private String id;

	@Column
	private String name;

	@Column
	private String ip;
	
	@Lob
	private String publicKey;
	
	/*
	 * @OneToMany(mappedBy="device") private List<Push> pushList;
	 */
	
	@Column
	private String port;
	
	@Basic
	private Timestamp lastUpdate;
	
	@Transient
	private String sessionToken;
	
	public Device() {
	}
	
	public Device(String id) {
		this.id = id;
	}
	
	public Device(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}
	
	public Device(String id, String ip, String port) {
		this.id = id;
		this.ip = ip;
		this.port = port;
	}
	
	public Device(DeviceForm form){
		this.id = form.getId();
		this.name = form.getName();
		this.ip = form.getIp();
		this.publicKey = form.getPublicKey();
		this.lastUpdate =  Timestamp.from(Instant.now());
	}
	
	public Device(DeviceUpdateForm form){
		this.name = form.getName();
		this.ip = form.getIp();
		this.publicKey = form.getPublicKey();
		this.lastUpdate =  Timestamp.from(Instant.now());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publickey) {
		this.publicKey = publickey;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSessionToken() {
		return this.sessionToken;
	}
	
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getSessionTokenTreated() {
		if (!this.checkIfSessionTokenIsValid()) {
			this.sessionToken = this.getNewSessionToken();
		}
		//System.out.println(sessionToken);
		return this.sessionToken;
	}

	public void renewSessionToken() {
		this.sessionToken = this.getNewSessionToken();
	}
	
	public String getNewSessionToken() {
		HttpRequest request;
		HttpResponse<String> response;
		String URLContext = "/login.fcgi";
		String body = "{\n" 
					+ "    \"login\": \"admin\",\n" 
					+ "    \"password\": \"admin\"\n" 
					+ "}";
		
		try {
			request = HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + this.ip + ":" + this.port + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.POST(HttpRequest.BodyPublishers.ofString(body)).build();
			response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			return new JSONObject(response.body()).getString("session");
		} catch (IOException e) {
			System.out.println("Problema ao gerar session token do equipamento: " + this.getIp());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Problema ao gerar session token do equipamento: " + this.getIp());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.out.println("Problema ao gerar session token do equipamento: " + this.getIp());
			e.printStackTrace();
		}
		throw new RuntimeException("Erro ao executar renovação de token");

	}
	
	public boolean checkIfSessionTokenIsValid() {
		HttpRequest request;
		HttpResponse<String> response;
		String URLContext = "/session_is_valid.fcgi?session=" + this.sessionToken;
		try {
			request = HttpRequest.newBuilder().uri(new URI(ConfigEnums.defaultHttpProtocol.valor + this.ip + ":" + this.port + URLContext))
				.headers("Content-Type", "application/json;charset=UTF-8")
				.timeout(Duration.ofSeconds(Integer.valueOf(ConfigEnums.defaultTimeout_ServerToFacials.valor)))
				.POST(HttpRequest.BodyPublishers.ofString("")).build();
			response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			return new JSONObject(response.body()).getBoolean("session_is_valid");
			
		} catch (IOException e) {
			System.out.println("Problema ao checar se session token do equipamento e valido: " + this.getIp());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Problema ao checar se session token do equipamento e valido: " + this.getIp());
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.out.println("Problema ao checar se session token do equipamento e valido: " + this.getIp());
			e.printStackTrace();
		}
		
		throw new RuntimeException("Erro ao executar chamada de tentativa de verificação de sessão do token está valida");

	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		return Objects.equals(id, other.id);
	}

	
}
