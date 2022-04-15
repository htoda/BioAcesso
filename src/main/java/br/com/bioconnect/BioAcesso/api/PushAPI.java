package br.com.bioconnect.BioAcesso.api;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bioconnect.BioAcesso.configuration.ServerConfig;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.Push;
import br.com.bioconnect.BioAcesso.model.dto.PushDto;
import br.com.bioconnect.BioAcesso.model.form.PushForm;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;
import br.com.bioconnect.BioAcesso.repository.PushRepository;
import br.com.bioconnect.BioAcesso.service.PhotoSyncronizationService;


/**
 * Introdução ao Push - https://www.controlid.com.br/docs/access-api-pt/modo-push/introducao-ao-push/
 */

@RestController
public class PushAPI {
	
	
	@Autowired
	private IDeviceRepository deviceRepo;
	
	@Autowired
	private PushRepository pushRepo;
	
	@Autowired
	private PhotoSyncronizationService photoSyncronizationService;
	
	@Autowired
	private ServerConfig serverConfig;

	
	@GetMapping("/push")
	public ResponseEntity<?> push(@RequestParam Map<String, String> params, HttpServletRequest request) {
		//System.out.println("\n\nPushAPI.push (GET)");
		
		this.processInitialDevicePush(request);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/result")
	public ResponseEntity<?> result(@RequestBody String response, @RequestParam Map<String, String> params, HttpServletRequest request) {
		System.out.println("\n\nPushAPI.result");
		System.out.println("response = [" + response + "], requestParams = [" + params + "]");
		//objectNode = [{"object_changes":[{"object":"templates","type":"inserted","values":{"id":"9","finger_position":"0","finger_type":"0","template":"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","user_id":"1"}}],"device_id":1037789,"time":1613253840}]

		//code

		return ResponseEntity.ok().build();
	}
	
	
	private void processInitialDevicePush (HttpServletRequest request) {
		/**
		 * Ao receber um push o server deverá
		 * 1. Avaliar se o device já está cadastrado
		 *   1.1. Caso o device não esteja cadastrado -> cadastrar o device 
		 *   1.2. Caso o device esteja cadastrado -> atualizar as informações base
		 * 2. Enviar a solicitação de listagem de usuários cadastrados no device e avaliar o timestamp de cada usuário do device
		 * 2.1. Recuperar lista de usuários cadastrados com fotos do device
		 * 2.2. Recuperar lista de usuários cadastrados no do banco de dados
		 * 2.3. Imagens existentes apenas no device -> cadastrar imagem no banco de dados
		 * 2.4. Imagens existentes apenas no banco de dados -> cadastrar imagem no device
		 * 2.5. Imagens existentes em ambos: a parte que tiver timestamp mais atualizado deverá atualizar a parte remota 
		 */		
		
		Device device;
		String deviceId = request.getParameter("deviceId");
		String addr = request.getRemoteAddr();
		
		System.out.println("\n\nPushAPI.push (GET) - Device: " + addr + " - " + deviceId);
		
		
		// 1. Avaliar se o device já está cadastrado no banco de dados
		Optional<Device> deviceOpt = deviceRepo.findById(deviceId);
		if (!deviceOpt.isPresent()) {
			//return ResponseEntity.notFound().build();
			//1.1. Caso o device não esteja cadastrado -> cadastrar o device
			device = new Device();
			device.setId(deviceId);
			device.setIp(addr);
			device.setPort(serverConfig.getDevicePort());
			this.deviceRepo.save(device);
		} else {
			//1.2. Caso o device esteja cadastrado -> atualizar as informações base de dados do servidor
			device = deviceOpt.get();
			if (!device.getIp().equals(addr)) {
				device.setIp(addr);
				this.deviceRepo.save(device);
			}
		}
		System.out.println(this.photoSyncronizationService.doPhotoSyncronization(device));
	}
	
	/*
	private ResponseEntity<?> bodyError(HttpServletRequest request) {
		String addr = request.getRemoteAddr();
		String host = request.getRemoteHost();
		String user = request.getRemoteUser();
		String deviceId = request.getParameter("deviceId");
		String p = request.getParameter("p");
		String uuid = request.getParameter("uuid");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		System.out.println("Remote Address: " + addr);
		System.out.println("Remote Host: " + host);
		//System.out.println("Remote User: " + user);
		//System.out.println("Remote IP: " + ipAddress);
		System.out.println("DeviceId: " + deviceId);
		//System.out.println("P: " + p);
		System.out.println("UUID: " + uuid);
		
		if (deviceId == null) {
			return ResponseEntity.badRequest().build();
		}
		
		
		return null;
	}
	*/
	
	@PostMapping("/push")
	@Transactional
	public ResponseEntity<PushDto> cadastrar (@RequestBody @Valid PushForm form, UriComponentsBuilder uriBuilder) {
		Push obj = form.converter(form, deviceRepo);
		pushRepo.save(obj);
		
		URI uri = uriBuilder.path("/push/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(new PushDto(obj));
	}


}
