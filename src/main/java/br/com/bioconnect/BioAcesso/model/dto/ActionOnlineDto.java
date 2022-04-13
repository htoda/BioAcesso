package br.com.bioconnect.BioAcesso.model.dto;

public class ActionOnlineDto {
	private String action;
	private String parameters;
	
	public ActionOnlineDto(String action, String parameters) {
		this.action = action;
		this.parameters = parameters;
	}

	public String getAction() {
		return action;
	}

	public String getParameters() {
		return parameters;
	}

}
