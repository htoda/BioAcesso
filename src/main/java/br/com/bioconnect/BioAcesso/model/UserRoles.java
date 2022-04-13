package br.com.bioconnect.BioAcesso.model;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

/**
 * @author hugo
 * Relaciona usuários a níveis de privilégio. Contém apenas usuários que tenham algum nível de privilégio diferente do padrão.
 */

@Entity (name = "user_roles")
public class UserRoles {
	
	@Id
	@Column (name = "user_id")
	private Integer userId;

	@Column
	private Integer roleId;
	
	@ManyToOne
    private User usuario;
	
	@Column (name = "data_criacao")
	@CreationTimestamp
	private Timestamp dataCriacao;
	
	public UserRoles() {
	}

	public UserRoles(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoles other = (UserRoles) obj;
		return Objects.equals(roleId, other.roleId) && Objects.equals(userId, other.userId);
	}
	
}
