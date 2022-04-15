package br.com.bioconnect.BioAcesso.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bioconnect.BioAcesso.configuration.ServerConfig;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.service.message.ConfigMessage;

@Service
public class ConfigService {
	
	@Autowired
	private ConfigMessage configMessage;

	@Autowired 
	private ServerConfig serverConfig;

	public Boolean setOnlineMode(Device device) {
		try {
			HttpRequest req = this.configMessage.getSetOnlineMode(device);
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				return true;
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	
	// PRE-REQUISITO: cadastrar o controller no device
	public Boolean setOnlineServer(Device device) {
		HttpRequest req;
		HttpResponse<String> response;
		String deviceId = null; 
		
		try {
			deviceId = this.recuperarIdControllerNoDevice(device);
			
			if (deviceId == null) return false;
			
			// setar o servidor online
			req = this.configMessage.getSetOnlineServer(device,deviceId);
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				return true;
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	
	private String recuperarIdControllerNoDevice (Device device) {
		HttpRequest req;
		HttpResponse<String> response;
		
		// recuperar o servidor do device 
		try {
			req = this.configMessage.getLoadObject(device, "devices");
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				JSONObject json_obj = new JSONObject(response.body());
				JSONArray image_info_array = json_obj.getJSONArray("devices");
				JSONObject json_obj_item;
				for (Object item : image_info_array) {
					json_obj_item = new JSONObject(item.toString());
					if (json_obj_item.getString("ip").contains(serverConfig.getServerIpAddress())) {
						return String.valueOf(json_obj_item.getBigInteger("id"));
					}
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Boolean setPushConfigurarion(Device device) {
		try {
			HttpRequest req = this.configMessage.getSetPushConfiguration(device);
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				return true;
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}

	public Boolean setMonitorConfigurarion(Device device) {
		try {
			HttpRequest req = this.configMessage.getSetMonitorConfiguration(device);
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				return true;
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	
	public String getLoadObject(Device device, String objeto) {
		try {
			HttpRequest req = this.configMessage.getLoadObject(device, objeto);
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				return response.body();
			} else {
				return "Erro no processo";
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "Erro no processo";
	}
	
	public Boolean getCreateControllerObject(Device device) {
		HttpRequest req;
		HttpResponse<String> response;
		
		// verifica se o controlador já está configurado
		if (this.recuperarIdControllerNoDevice(device) != null) return true;
		
		try {
			// caso nao esteja configurado, devera ser inserido
			req = this.configMessage.getInsertControllerObject(device);
			response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				return true;
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}

	public String initialConfig(Device device) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Resultado da configuração do dispositivo " + device.getIp() + ":");
		sb.append("\n");
		
		if (this.setPushConfigurarion(device)) {
			sb.append("Configurar servidor de Push configuration: ok");
		} else {
			sb.append("Configurar servidor de Push: erro");
		}
		sb.append("\n");
	
		if (this.setMonitorConfigurarion(device)) {
			sb.append("Configurar servidor de Monitor: ok");
		} else {
			sb.append("Configurar servidor de Monitor: erro");
		}
		sb.append("\n");
		
		if (this.getCreateControllerObject(device)) {
			sb.append("Inserir servidor de Controller: ok");
		} else {
			sb.append("Inserir servidor de Controller: erro");
		}
		sb.append("\n");
		
		if (this.setOnlineServer(device)) {
			sb.append("Configurar servidor online: ok");
		} else {
			sb.append("Configurar servidor online: erro");
		}
		sb.append("\n");
		
		if (this.setOnlineMode(device)) {
			sb.append("Configurar device em modo online: ok");
		} else {
			sb.append("Configurar device em modo online: erro");
		}
		sb.append("\n");
		
		return sb.toString();
	}

	public boolean cleanUsersDeviceDatabase(Device device) {
		try {
			HttpRequest req = this.configMessage.getDestroyObject(device, "users");
			HttpResponse<String> response = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
			if (response.statusCode() == 200)  {
				return true;
			} else {
				return false;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return false;
	}
	
}
