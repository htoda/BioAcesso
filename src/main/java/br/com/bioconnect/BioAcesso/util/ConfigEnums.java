package br.com.bioconnect.BioAcesso.util;

public enum ConfigEnums {
	defaultHttpProtocol("http://"),
	defaultTimeout_ServerToFacials("5"),
	defaultTimeout_ServerToFacialsBatch("90"),
	defaultTimeout_ServerToFacials_Enroll("30"),
	defaultTimeout_FacialsToServer("5");
	
	public String valor;
	
	ConfigEnums(String valor) {
		this.valor = valor;
	}

}
