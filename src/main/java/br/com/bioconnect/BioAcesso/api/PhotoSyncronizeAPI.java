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
import br.com.bioconnect.BioAcesso.service.PhotoSyncronizationService;

@RestController
@RequestMapping("/photo_syncronization")
public class PhotoSyncronizeAPI {

	@Autowired
	private PhotoSyncronizationService photoSyncronizationService;

	@Autowired
	private ServerConfig serverConfig;

	@GetMapping("/sync/{ipAdress}")
	public ResponseEntity<?> doPhotoSyncronization(@RequestParam Map<String, String> params, HttpServletRequest request,
			@PathVariable String ipAdress) {
		System.out.println("\n\nPhotoSyncronizeAPI.doPhotoSyncronization");

		String retorno = this.photoSyncronizationService
				.doPhotoSyncronization(new Device(null, ipAdress, serverConfig.getDevicePort()));

		if (retorno == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok().body(retorno);
		}
	}

	@GetMapping("/full_load/{ipAdress}")
	public ResponseEntity<?> doFullPhotoLoadInDevice(@RequestParam Map<String, String> params,
			HttpServletRequest request, @PathVariable String ipAdress) {
		System.out.println("\n\nPhotoSyncronizeAPI.doFullPhotoLoadInDevice");

		String retorno = this.photoSyncronizationService
				.doFullPhotoLoadInDevice(new Device(null, ipAdress, serverConfig.getDevicePort()));

		if (retorno == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok().body(retorno);
		}
	}

	@GetMapping("/copy_user_photo_from_device/{ipAdress}")
	public ResponseEntity<?> copyUserAndPhotosFromDevice(@RequestParam Map<String, String> params,
			HttpServletRequest request, @PathVariable String ipAdress) {
		System.out.println("\n\nPhotoSyncronizeAPI.doFullPhotoLoadInDevice");

		this.photoSyncronizationService
				.copyAndPersistUserAndPhotosFromDevice(new Device(null, ipAdress, serverConfig.getDevicePort()));

		return ResponseEntity.ok().body("ok");

	}
}
