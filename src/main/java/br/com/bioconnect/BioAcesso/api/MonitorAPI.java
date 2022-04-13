package br.com.bioconnect.BioAcesso.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class MonitorAPI {
	
	/**
	 * 
	 * @param objectNode
	 * @param params
	 * @return
	 * 
	 * Cadastro remoto
	 * 
	 *  https://www.controlid.com.br/docs/access-api-pt/monitor/introducao-ao-monitor/
	 *  
		POST /api/notifications/template  
		Enviado quando um template é cadastrado remotamente. Veja Cadastro Remoto Biometria Facial Cartão para mais informações.
		
		POST /api/notifications/user_image
		Enviado quando uma face é cadastrada remotamente. Veja Cadastro Remoto Biometria Facial Cartão para mais informações.
		
		POST /api/notifications/card
		Enviado quando um cartão é cadastrado remotamente. Veja Cadastro Remoto Biometria Facial Cartão para mais informações.
	 */
	
	/**
	 * Enviado quando uma face é cadastrada remotamente
	 * @param objectNode
	 * @return
	 */
	@RequestMapping("/api/notifications/user_image")
	public ResponseEntity<?> userImage(@RequestBody ObjectNode objectNode) {
		System.out.println("\n\nMonitorAPI.userImage");
		System.out.println("\n\nEnviado quando uma face é cadastrada remotamente");
		System.out.println("objectNode = [" + objectNode + "]");

		return ResponseEntity.ok().build();
	}
	
	/**
	 * Enviado quando um template é cadastrado remotamente.
	 * @param objectNode
	 * @return
	 */
	@RequestMapping("/api/notifications/template")
	public ResponseEntity<?> template(@RequestBody ObjectNode objectNode) {
		System.out.println("\n\nMonitorAPI.template");
		System.out.println("\n\nEnviado quando um template é cadastrado remotamente.");
		System.out.println("objectNode = [" + objectNode + "]");

		return ResponseEntity.ok().build();
	}
	
	/**
	 * Enviado quando há alterações na tabela access_logs (inserção, alteração ou deleção) e 
	 * também monitora as alterações realizadas na tabela alarm_logs (inserção, alteração ou deleção).
	 * @param objectNode
	 * @param params
	 * @return
	 *  */
	@RequestMapping("/api/notifications/dao")
	public ResponseEntity<?> dao(@RequestBody ObjectNode objectNode, @RequestParam Map<String, String> params) {
		System.out.println("\n\nMonitorAPI.dao");
		System.out.println("objectNode = [" + objectNode + "]");
		
		//Exemplo de tentativa de acesso em foto cadastrada:

//MonitorAPI.dao
//objectNode = [{"object_changes":[{"object":"access_logs","type":"inserted","values":{"id":"399","time":"1648487955","event":"3","device_id":"4408801109217724","identifier_id":"1717658368","user_id":"","portal_id":"","identification_rule_id":"","card_value":"0","qrcode_value":"","detection_confidence":"1364","mask":"0","log_type_id":"-1"}}],"device_id":4408801109217724,"time":1648487956}]

		return ResponseEntity.ok().build();
	}

	@RequestMapping("/api/notifications/card")
	public ResponseEntity<?> card(@RequestBody ObjectNode objectNode) {
		System.out.println("\n\nMonitorAPI.card");
		System.out.println("objectNode = [" + objectNode + "]");

		return ResponseEntity.ok().build();
	}

	@RequestMapping("/api/notifications/catra_event")
	public ResponseEntity<?> catraEvent(@RequestBody ObjectNode objectNode) {
		System.out.println("\n\nMonitorAPI.catraEvent");
		System.out.println("objectNode = [" + objectNode + "]");

		return ResponseEntity.ok().build();
	}

	@RequestMapping("/api/notifications/operation_mode")
	public ResponseEntity<?> operationMode(@RequestBody ObjectNode objectNode) {
		System.out.println("\n\nMonitorAPI.operationMode");
		System.out.println("objectNode = [" + objectNode + "]");

		return ResponseEntity.ok().build();
	}

	@RequestMapping("/api/notifications/door")
	public ResponseEntity<?> door(@RequestBody ObjectNode objectNode) {
		System.out.println("\n\nMonitorAPI.door");
		System.out.println("objectNode = [" + objectNode + "]");

		return ResponseEntity.ok().build();
	}

	@RequestMapping("/api/notifications/secbox")
	public ResponseEntity<?> secbox(@RequestBody ObjectNode objectNode) {
		System.out.println("\n\nMonitorAPI.secbox");
		System.out.println("objectNode = [" + objectNode + "]");

		return ResponseEntity.ok().build();
	}
	
}
