package br.com.bioconnect.BioAcesso.model.dto;

public class HomeDto {
	private String name;
	private String version;
	private String description;
	
	public HomeDto() {
		this.name = "BioAcesso";
		this.version = "1.0.0";
		this.description = "API de Conntrole de Acesso";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	};
}
