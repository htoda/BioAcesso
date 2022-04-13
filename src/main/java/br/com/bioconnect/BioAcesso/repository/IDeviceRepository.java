package br.com.bioconnect.BioAcesso.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bioconnect.BioAcesso.model.Device;

public interface IDeviceRepository extends JpaRepository<Device, String> {

	// https://www.devmedia.com.br/persistencia-com-spring-data-jpa/24390
	
	Page<Device> findByName(String name, Pageable paginacao);
	
	Optional<Device> findByIp(String ipAdress);

	List<Device> findAllByOrderByIdAsc();

}
