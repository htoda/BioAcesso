package br.com.bioconnect.BioAcesso.api;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bioconnect.BioAcesso.configuration.ServerConfig;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.User;
import br.com.bioconnect.BioAcesso.model.dto.UserDto;
import br.com.bioconnect.BioAcesso.model.form.UserFormCadastroRemoto;
import br.com.bioconnect.BioAcesso.service.CadastroRemotoService;

@RestController
@RequestMapping("/remote_enroll")
public class CadastroRemotoAPI {

	@Autowired
	private CadastroRemotoService cadastroRemotoService;

	@Autowired
	private ServerConfig serverConfig;

	@PostMapping("/")
	public ResponseEntity<?> doRemoteEnroll(@RequestBody UserFormCadastroRemoto userForm,UriComponentsBuilder uriBuilder) {
		System.out.println("\n\nCadastroRemotoAPI.doRemoteEnroll");

		User user = userForm.converter(userForm);

		boolean retorno = this.cadastroRemotoService.doRemoteEnroll(
				new Device(userForm.getDeviceId(), userForm.getDeviceIp(), serverConfig.getDevicePort())
				,user);

		if (!retorno) {
			return ResponseEntity.badRequest().build();
		} else {
			URI uri = uriBuilder.path("/remote_enroll/{id}").buildAndExpand(user.getUserId()).toUri();
			return ResponseEntity.created(uri).body(new UserDto(user));
		}
	}
	
	@GetMapping("/execute_broadcast")
	public ResponseEntity<?> doBroadcast(HttpServletRequest request) {
		System.out.println("\n\nCadastroRemotoAPI.doBroadcast");

		if (this.cadastroRemotoService.doBroadcast()) {
			return ResponseEntity.ok().body("Broadcast de mensagens realizado com sucesso, sem mensagens pendentes");
		} else {
			return ResponseEntity.ok().body("Broadcast de mensagens realizado parcialmente, existem mensagens pendentes que não puderam ser enviadas");
		}
	}
}
