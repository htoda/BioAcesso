package br.com.bioconnect.BioAcesso.api;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.dto.DeviceDto;
import br.com.bioconnect.BioAcesso.model.form.DeviceForm;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;

@RestController
@RequestMapping("/devices")
public class DevicesAPI {
	
	@Autowired
	private IDeviceRepository mainRepo;
	
	@GetMapping
	@Cacheable(value = "listaDeDevices")
	public Page<DeviceDto> lista(@RequestParam(required = false) String name, 
			@PageableDefault(sort = "name", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		
		if (name != null) {
			Page<Device> ret = mainRepo.findByName(name, paginacao);
			return DeviceDto.converter(ret);
		}
		
		Page<Device> ret = mainRepo.findAll(paginacao);
		return DeviceDto.converter(ret);
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "listaDeDevices", allEntries = true)
	public ResponseEntity<DeviceDto> cadastrar(@RequestBody @Valid DeviceForm form, UriComponentsBuilder uriBuilder) {
		Device obj = form.converter(form);
		mainRepo.save(obj);

	   if (obj == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        URI uri = uriBuilder.path("/devices/{id}").buildAndExpand(obj.getId()).toUri();
	        return ResponseEntity.created(uri).body(new DeviceDto(obj));
	    }
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DeviceDto> detalhar(@PathVariable String id) {
		Optional<Device> obj = mainRepo.findById(id);
		if (obj.isPresent()) {
			return ResponseEntity.ok(new DeviceDto(obj.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	/*
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeSetores", allEntries = true)
	public ResponseEntity<SetorDto> atualizar(@PathVariable Long id, @RequestBody @Valid SetorForm form) {
		Optional<Setor> optional = mainRepo.findById(id);
		if (optional.isPresent()) {
			Setor setor = form.atualizar(id, mainRepo);
			return ResponseEntity.ok(new SetorDto(setor));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaDeSetores", allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Setor> optional = mainRepo.findById(id);
		if (optional.isPresent()) {
			mainRepo.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	*/
	
	//https://www.baeldung.com/spring-boot-json
}