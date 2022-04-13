package br.com.bioconnect.BioAcesso.model.form;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.bioconnect.BioAcesso.model.User;

public class UserForm {

	private BigInteger userId;
	
	@NotNull @NotEmpty
	private String registration;
	
	@NotNull @NotEmpty
	private String name;

	private String password;

	private String salt;
	
	private Long expires;
	
	@NotNull
	private Integer userTypeId;
	
	private Long beginTime;
	
	private Long endTime;
	
    private byte[] foto;
	
	private Long imageTimestamp;
	
	@NotNull
    private Integer groupId;
    
	private Integer roleId;
	
	private String turma;

	private String controle2;
    
	private String controle3;

	public User converter(UserForm obj) {
		return new User(obj);
	}
	
	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getExpires() {
		return expires;
	}

	public void setExpires(Long expires) {
		this.expires = expires;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Long getImageTimestamp() {
		return imageTimestamp;
	}

	public void setImageTimestamp(Long imageTimestamp) {
		this.imageTimestamp = imageTimestamp;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getControle2() {
		return controle2;
	}

	public void setControle2(String controle2) {
		this.controle2 = controle2;
	}

	public String getControle3() {
		return controle3;
	}

	public void setControle3(String controle3) {
		this.controle3 = controle3;
	}
	
}
