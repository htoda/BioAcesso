package br.com.bioconnect.BioAcesso.model.dto;

import br.com.bioconnect.BioAcesso.model.UserRoles;

public class UserRolesDTO {
	
	private Integer userId;

	private	Integer roleId;
	
	public UserRolesDTO() {
	}

	public UserRolesDTO(UserRoles l) {
		this.userId = l.getUserId();
		this.roleId = l.getRoleId();
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

}
