package br.com.bioconnect.BioAcesso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bioconnect.BioAcesso.model.Push;

public interface PushRepository extends JpaRepository<Push, String> {
	
	//Page<Push> findByDeviceId(Long id, Pageable paginacao);
	
	Optional<Push> findFirstByDevice_IdOrderByIdAsc(Long deviceId);
	
	//@Query("select p from Pessoa p where (p.nome LIKE ?1) OR p.cpf LIKE ?1")
	//@Query(value = "select f.* from foto f left join pessoa p on f.pessoa_id=p.id where p.id = ?1", nativeQuery = true)
	//Page<Push> listaPorPalavraChave(String palavra, Pageable paginacao);

}
