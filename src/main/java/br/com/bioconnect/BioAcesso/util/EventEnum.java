package br.com.bioconnect.BioAcesso.util;

public enum EventEnum {
	equipamento_invalido("Equipamento inválido",1),
	parametros_de_regra_de_identificacao_invalidos("Parâmetros de regra de identificação inválidos",2),
	nao_identificado("Não identificado",3),
	identificacao_pendente("Identificação pendente",4),
	timeout_na_identificacao("Timeout na identificação",5),
	acesso_negado("Acesso negado",6),
	acesso_autorizado("Acesso autorizado",7),
	acesso_pendente("Acesso pendente(usado quando o acesso depende de mais de uma pessoa)",8),
	usuario_nao_administrador("Usuário não é administrador (usado quando um usuário tenta acessar o menu mas não é administrador)",9),
	acesso_nao_identificado("Acesso não identificado (quando o portal é aberto através da API e o motivo não é informado)",10),
	acesso_atraves_de_botoeira("Acesso através de botoeira",11),
	acesso_atraves_de_interface_web("Acesso através da interface WEB",12),
	desistencia_de_entrada("Desistência de entrada (somente utilizado pela catraca)",13);
	
	public int valor;
	public String descricao;
	
	EventEnum(String descricao,int valor) {
		this.valor = valor;
		this.descricao = descricao;
	}

}
