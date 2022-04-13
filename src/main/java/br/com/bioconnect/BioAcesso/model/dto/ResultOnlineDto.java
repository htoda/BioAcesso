package br.com.bioconnect.BioAcesso.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultOnlineDto {
	private int event;
	private int user_id;
	private String user_name;
	private boolean user_image;
	private String user_image_hash;
	private int portal_id;
	private List<ActionOnlineDto> actions;
	private String message;
	
	public ResultOnlineDto() {
		this.actions = new ArrayList<ActionOnlineDto>();
	}
	
	public ResultOnlineDto(
			int event,
			int user_id,
			String user_name,
			boolean user_image,
			String user_image_hash,
			int portal_id,
			List<ActionOnlineDto> actions,
			String message) {
		this.event = event;
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_image = user_image;
		this.user_image_hash = user_image_hash;
		this.portal_id = portal_id;
		this.actions = actions;
		this.message = message;		
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public boolean isUser_image() {
		return user_image;
	}

	public void setUser_image(boolean user_image) {
		this.user_image = user_image;
	}

	public String getUser_image_hash() {
		return user_image_hash;
	}

	public void setUser_image_hash(String user_image_hash) {
		this.user_image_hash = user_image_hash;
	}

	public int getPortal_id() {
		return portal_id;
	}

	public void setPortal_id(int portal_id) {
		this.portal_id = portal_id;
	}

	public List<ActionOnlineDto> getActions() {
		return actions;
	}

	public void setActions(List<ActionOnlineDto> actions) {
		this.actions = actions;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/*
		event	int	Tipo do evento, pode ser:
			1) Equipamento inválido
			2) Parâmetros de regra de identificação inválidos
			3) Não identificado
			4) Identificação pendente
			5) Timeout na identificação
			6) Acesso negado
			7) Acesso autorizado
			8) Acesso pendente(usado quando o acesso depende de mais de uma pessoa)
			9) Usuário não é administrador (usado quando um usuário tenta acessar o menu mas não é administrador)
			10) Acesso não identificado (quando o portal é aberto através da API e o motivo não é informado)
			11) Acesso através de botoeira
			12) Acesso através da interface WEB
			13) Desistência de entrada (somente utilizado pela catraca)
		user_id	int	ID do usuário, em caso de identificação.
		user_name	string	Nome do usuário, em caso de identificação.
		user_image	bool	Usuário identificado possui ou não foto.
		user_image_hash	string	Caso o usuário identificado possua imagem, envia o hash (SHA1) da mesma. O equipamento usa esse valor para saber se ele possui a imagem em cache ou se precisa pedir a imagem ao servidor.
		portal_id	string	ID do portal correspondente.
		actions	Array de Objetos JSON	Ações que devem ser executas pelo equipamento. Exemplo: [ {"action":"door", "parameters":"door=1"}, {"action":"door", "parameters":"door=2"} ]
		message	string	Mensagem a ser exibida no display no momento do acesso.
	*/
}
