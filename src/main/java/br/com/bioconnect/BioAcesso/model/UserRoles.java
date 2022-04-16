package br.com.bioconnect.BioAcesso.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

/**
 * @author hugo
 * Relaciona usuários a níveis de privilégio. Contém apenas usuários que tenham algum nível de privilégio diferente do padrão.
 */

@Entity (name = "user_roles")
public class UserRoles {
	
	@Id
	@SequenceGenerator(name = "user_roles_seq", sequenceName = "user_roles_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_roles_seq")
	@Column(name = "user_roles_id", updatable = false)
	private Integer userRolesId;

	@ManyToOne
    private User usuario;
	
	@Column
	private Integer roleId;
	
	@Column (name = "data_criacao")
	@CreationTimestamp
	private Timestamp dataCriacao;
	
	public UserRoles() {
	}

	public UserRoles(Integer roleId, User user) {
		this.roleId = roleId;
		this.usuario = user;
	}

	public Integer getUserRolesId() {
		return userRolesId;
	}

	public void setUserRolesId(Integer userRolesId) {
		this.userRolesId = userRolesId;
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

	
}
