package br.com.bioconnect.BioAcesso.service.message;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioconnect.BioAcesso.configuration.ServerConfig;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.util.ConfigEnums;

@Service
public class ConfigMessage {
	
	@Autowired 
	private ServerConfig serverConfig;
	 
	public HttpRequest getSetPushConfiguration(Device device) throws URISyntaxException {
		String pushRequestPeriod = "120";
		String URLContext = "/set_configuration.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "	\"push_server\": {\n" 
					+ "    \"push_remote_address\": \"" + serverConfig.getServerIpAddress() + ":" + serverConfig.getServerPort() + "\",\n"
					+ "    \"push_request_timeout\": \"" + ConfigEnums.defaultTimeout_FacialsToServer.valor + "\",\n"
					+ "    \"push_request_period\": \"" + pushRequestPeriod + "\"\n" 
					+ "}\n" 
					+ "}";

		return this.postRequestBuilder(device, body, URLContext);
	}
	
	public HttpRequest getSetMonitorConfiguration(Device device) throws URISyntaxException {
		String URLContext = "/set_configuration.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "	\"monitor\": {\n" 
					+ "    \"hostname\": \"" + serverConfig.getServerIpAddress() + "\",\n"
					+ "    \"port\": \"" + serverConfig.getServerPort() + "\",\n"
					+ "    \"request_timeout\": \"" + ConfigEnums.defaultTimeout_FacialsToServer.valor + "\"\n"
					+ "}\n" 
					+ "}";

		return this.postRequestBuilder(device, body, URLContext);
	}
	
	public HttpRequest getSetOnlineMode(Device device) throws URISyntaxException {
		boolean online = true;
		boolean localIdentification = true;
		
		String URLContext = "/set_configuration.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "	\"general\": {\n" 
					+ "    \"online\": \"" + (online ? "1" : "0") + "\",\n"
					+ "    \"local_identification\": \"" + (localIdentification ? "1" : "0") + "\"\n"
					+ "}\n" 
					+ "}";

		return this.postRequestBuilder(device, body, URLContext);
	}	
	
	public HttpRequest getSetOnlineServer(Device device, String serverId) throws URISyntaxException {
		String URLContext = "/set_configuration.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "	\"online_client\": {\n" 
					+ "    \"server_id\": \"" + serverId + "\"\n" 
					+ "}\n" 
					+ "}";

		return this.postRequestBuilder(device, body, URLContext);
	}
	
	public HttpRequest getLoadObject(Device device, String objectName) throws URISyntaxException {
		String URLContext = "/load_objects.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "    \"object\": \"" + objectName + "\"\n"
					+ "}";

		return this.postRequestBuilder(device, body, URLContext);
	}
	
	
	public HttpRequest getDestroyObject(Device device, String objectName) throws URISyntaxException {
		String URLContext = "/destroy_objects.fcgi?session=" + device.getSessionTokenTreated();
		String body = "{\n" 
					+ "    \"object\": \"" + objectName + "\"\n"
					+ "}";

		return this.postRequestBuilder(device, body, URLContext);
	}

	public HttpRequest getInsertControllerObject(Device device) throws URISyntaxException {
		String serverName = "Controller";
		String serverIP= "http://" + serverConfig.getServerIpAddress();
		String URLContext = "/create_objects.fcgi?session=" + device.getSessionTokenTreated();
		
		String body = "{\n" 
					+ "    	\"object\": \"devices\",\n"
					+ "		\"values\": [{\n" 
					+ "    		\"name\": \"" + serverName + "\",\n"
					+ "    		\"ip\": \"" + serverIP + ":" + serverConfig.getServerPort() + "\",\n"
					+ "    		\"public_key\": \"\"\n" 		
					+ "		}]\n" 
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
