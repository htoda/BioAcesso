package br.com.bioconnect.BioAcesso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bioconnect.BioAcesso.model.Group;

public interface IGroupRepository extends JpaRepository<Group, String> {

}
