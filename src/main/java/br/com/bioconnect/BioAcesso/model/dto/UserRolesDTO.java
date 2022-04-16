package br.com.bioconnect.BioAcesso.model.dto;

import br.com.bioconnect.BioAcesso.model.UserRoles;

public class UserRolesDTO {
	
	private	Integer roleId;
	
	public UserRolesDTO() {
	}

	public UserRolesDTO(UserRoles l) {
		this.roleId = l.getRoleId();
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
