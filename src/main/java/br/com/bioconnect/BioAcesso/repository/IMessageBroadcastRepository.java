package br.com.bioconnect.BioAcesso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bioconnect.BioAcesso.model.MessageBroadcast;

public interface IMessageBroadcastRepository extends JpaRepository<MessageBroadcast, String> {

	List<MessageBroadcast> findAllByOrderByIdAsc();

}
