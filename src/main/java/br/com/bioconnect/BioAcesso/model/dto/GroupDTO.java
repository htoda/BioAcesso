package br.com.bioconnect.BioAcesso.model.dto;

import br.com.bioconnect.BioAcesso.model.Group;

public class GroupDTO {
	
	private Integer groupId;
	private String name;
	
	public GroupDTO(Integer groupId, String name) {
		super();
		this.groupId = groupId;
		this.name = name;
	}
	
	public GroupDTO(Group obj) {
		this.groupId = obj.getGroupId();
		this.name = obj.getName();
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
	
}
