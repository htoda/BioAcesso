package br.com.bioconnect.BioAcesso.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import br.com.bioconnect.BioAcesso.model.form.PushForm;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;

@Entity
public class Push {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String verb;

	private String endpoint;
	
	@Lob
	private String body;

	private String contentType = "";
	
	private String queryString = "";
	
	@ManyToOne
	private Device device;
	
	public Push() {
		
	}

	public Push(PushForm form, IDeviceRepository deviceRepo) {
		this.verb = form.getVerb();
		this.endpoint = form.getEndpoint();
		this.body = form.getBody();
		this.contentType = form.getContentType();
		this.queryString = form.getQueryString();
		this.device = deviceRepo.getOne(form.getDeviceId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
}
