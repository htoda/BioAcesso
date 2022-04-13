package br.com.bioconnect.BioAcesso.util;

public enum UserRolesEnum {
	administrador("Usuário administrador",1);
	
	public String descricao;
	public Integer codigo;
	
	UserRolesEnum(String descricao,Integer codigo) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

}
