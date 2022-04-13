package br.com.bioconnect.BioAcesso.util;

public enum ConfigEnums {
	defaultHttpProtocol("http://"),
	defaultTimeout_ServerToFacials("3"),
	defaultTimeout_ServerToFacials_Enroll("30"),
	defaultTimeout_FacialsToServer("3");
	
	public String valor;
	
	ConfigEnums(String valor) {
		this.valor = valor;
	}

}
