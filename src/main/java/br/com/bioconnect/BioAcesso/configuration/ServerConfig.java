package br.com.bioconnect.BioAcesso.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("infra")
public class ServerConfig {

    private String serverIpAddress; 
    private String serverPort;
    private String devicePort;
    
	public String getServerIpAddress() {
		return serverIpAddress;
	}
	public void setServerIpAddress(String ServerIpAddress) {
		this.serverIpAddress = ServerIpAddress;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public String getDevicePort() {
		return devicePort;
	}
	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}
	
}