package br.com.bioconnect.BioAcesso.api;

import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bioconnect.BioAcesso.model.User;
import br.com.bioconnect.BioAcesso.model.dto.UserDto;
import br.com.bioconnect.BioAcesso.model.form.UserForm;
import br.com.bioconnect.BioAcesso.repository.IUserRepository;

@RestController
@RequestMapping("/user")
public class UserAPI {
	
	@Autowired
	private IUserRepository userRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser (@RequestParam Map<String, String> params, HttpServletRequest request,@PathVariable String id) {
		
		Optional<User> usuario = this.userRepository.findById(new BigInteger(id));
		if (usuario.isPresent()) {
			return ResponseEntity.ok(new UserDto(usuario.get()));
		}
		return ResponseEntity.notFound().build();
	}		
	
	@GetMapping
	public ResponseEntity<List<UserDto>> listUser (@RequestParam Map<String, String> params, HttpServletRequest request) {
		return ResponseEntity.ok().body(UserDto.converter(this.userRepository.findAll()));
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<UserDto> cadastrar(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
		User obj = form.converter(form);
		userRepository.save(obj);

	   if (obj == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(obj.getUserId()).toUri();
	        return ResponseEntity.created(uri).body(new UserDto(obj));
	    }
		
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<UserDto> atualizar(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
		
		Optional<User> userOpt = this.userRepository.findById(form.getUserId());
		if (!userOpt.isPresent()) return ResponseEntity.notFound().build();
		User user = userOpt.get();
		userRepository.save(user.copyNewValues(form));

        return ResponseEntity.ok(new UserDto(user));
		
	}

}
