package br.com.bioconnect.BioAcesso.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.bioconnect.BioAcesso.model.Push;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;
import br.com.bioconnect.BioAcesso.repository.PushRepository;

public class PushForm {
	
	@NotNull
	private String deviceId;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String verb;

	@NotNull @NotEmpty @Length(min = 3)
	private String endpoint;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String body;

	private String contentType;
	
	private String queryString;
	
	public String getDeviceId() {
		return deviceId;
	}

	public String getVerb() {
		return verb;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public String getBody() {
		return body;
	}

	public String getContentType() {
		return contentType;
	}

	public String getQueryString() {
		return queryString;
	}
	
	public Push converter(PushForm obj, IDeviceRepository deviceRepo) {
		return new Push(obj, deviceRepo);
	}

	public Push atualizar(String id, PushRepository objRepository) {
		Push obj = objRepository.getOne(id);	
		obj.setVerb(this.verb);
		obj.setEndpoint(this.endpoint);
		obj.setBody(this.body);
		obj.setContentType(this.contentType);
		obj.setQueryString(this.getQueryString());
		return obj;
	}

}
