package br.com.bioconnect.BioAcesso.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bioconnect.BioAcesso.model.dto.HomeDto;

@RestController
public class SystemAPI {
	
	@GetMapping("/")
	public ResponseEntity<HomeDto> home(@RequestParam Map<String, String> params, HttpServletRequest request) {
		System.out.println("\n\nSyetemAPI.home");
		
		return ResponseEntity.ok().body(new HomeDto());
	}

}
