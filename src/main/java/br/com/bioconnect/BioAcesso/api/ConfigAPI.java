package br.com.bioconnect.BioAcesso.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bioconnect.BioAcesso.configuration.ServerConfig;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.service.ConfigService;

@RestController
@RequestMapping("/config")
public class ConfigAPI {
	
	@Autowired 
	private ServerConfig serverConfig;
	
	@Autowired
	private ConfigService configService;
	
	@GetMapping("/setonlinemode/{ipAdress}")
	public ResponseEntity<?> setOnlineMode(@RequestParam Map<String, String> params, HttpServletRequest request,@PathVariable String ipAdress) {
		
		if (this.configService.setOnlineMode(new Device(ipAdress,serverConfig.getDevicePort())))  {
			return ResponseEntity.ok().body("Configurações de OnlineMode atualizadas");
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/setpush/{ipAdress}")
	public ResponseEntity<?> setPushConfigurarion(HttpServletRequest request,@PathVariable String ipAdress) {
		
		if (this.configService.setPushConfigurarion(new Device(ipAdress,serverConfig.getDevicePort())))  {
			return ResponseEntity.ok().body("Configurações de Push atualizadas");
		} else {
			return ResponseEntity.badRequest().build();
		}
		
	}

	@GetMapping("/setmonitor/{ipAdress}")
	public ResponseEntity<?> setMonitorConfigurarion(HttpServletRequest request,@PathVariable String ipAdress) {

		if (this.configService.setMonitorConfigurarion(new Device(ipAdress,serverConfig.getDevicePort())))  {
			return ResponseEntity.ok().body("Configurações de Monitor atualizadas");
		} else {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	@GetMapping("/getobjectvalue/{ipAdress}/{objeto}")
	public ResponseEntity<?> getLoadObject(HttpServletRequest request,@PathVariable String ipAdress,@PathVariable String objeto) {

		String ret = this.configService.getLoadObject(new Device(ipAdress,serverConfig.getDevicePort()),objeto);
					
		return ResponseEntity.ok().body(ret);
				
	}
	
	@GetMapping("/getcreatecontrollerobject/{ipAdress}")
	public ResponseEntity<?> getCreateControllerObject(HttpServletRequest request,@PathVariable String ipAdress) {

		if (this.configService.getCreateControllerObject(new Device(ipAdress,serverConfig.getDevicePort())))  {
			return ResponseEntity.ok().body("Configurações de criação de server (controller) no device realizada com sucesso");
		} else {
			return ResponseEntity.badRequest().build();
		}
				
	}
	
	
	@GetMapping("/setonlineserver/{ipAdress}")
	public ResponseEntity<?> setOnlineServer(@RequestParam Map<String, String> params, HttpServletRequest request,@PathVariable String ipAdress) {
		
		if (this.configService.setOnlineServer(new Device(ipAdress,serverConfig.getDevicePort())))  {
			return ResponseEntity.ok().body("Configurações de setOnlineServer atualizadas");
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	@GetMapping("/initialize_device/{ipAdress}")
	public ResponseEntity<?> initialConfig(HttpServletRequest request,@PathVariable String ipAdress) {
		String retorno = null;
		retorno = this.configService.initialConfig(new Device(ipAdress,serverConfig.getDevicePort())); 
		if (retorno != null || retorno.isEmpty())  {
			return ResponseEntity.ok().body(retorno);
		} else {
			return ResponseEntity.badRequest().build();
		}
				
	}
	
	@GetMapping("/clean_users_in_device/{ipAdress}")
	public ResponseEntity<?> cleanUsersDeviceDatabase(HttpServletRequest request,@PathVariable String ipAdress) {

		if (this.configService.cleanUsersDeviceDatabase(new Device(ipAdress,serverConfig.getDevicePort())))  {
			return ResponseEntity.ok().body("Usuários do device de endereço IP " + ipAdress + " excluídos com sucesso");
		} else {
			return ResponseEntity.badRequest().build();
		}
				
	}
	
	
	
}
