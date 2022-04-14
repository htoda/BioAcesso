package br.com.bioconnect.BioAcesso.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity (name = "groups_device")
public class Group {
	
	@Id
	@Column (name = "group_id")
	private Integer groupId;

	@Column
	private String name;
	
	@ManyToMany (mappedBy = "listGroups")
    private Set<User> listUsuarios;
	
	@Column (name = "data_criacao")
	@CreationTimestamp
	private Timestamp dataCriacao;
	
	public Group() {
	}
	
	public Group(Integer group_id) {
		this.groupId = group_id;
	}
	
	public Group(Integer group_id, String name) {
		this.groupId = group_id;
		this.name = name;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(Set<User> listUsuarios) {
		this.listUsuarios = listUsuarios;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		return Objects.equals(groupId, other.groupId);
	}

	
}
