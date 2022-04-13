package br.com.bioconnect.BioAcesso.service;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.bioconnect.BioAcesso.model.Group;
import br.com.bioconnect.BioAcesso.repository.IGroupRepository;

@Component
public class RunAfterStartup {
	
	@Autowired
	private IGroupRepository groupRepository;
		
	@EventListener(ApplicationReadyEvent.class)
	@Transient
	public void runAfterStartup() {
		System.out.println("Algo a excecutar após iniciar o módulo?");
		
		groupRepository.save(new Group(1,"Padrão"));
	}
}