package br.com.bioconnect.BioAcesso.model.dto;

import br.com.bioconnect.BioAcesso.model.Push;

public class PushDto {

	private String verb;

	private String endpoint;
	
	private String body;

	private String contentType;
	
	private String queryString;

	public PushDto(Push obj) {
		this.verb = obj.getVerb();
		this.endpoint = obj.getEndpoint();
		this.body = obj.getBody();
		this.contentType = obj.getContentType();
		this.queryString = obj.getQueryString();
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

}
