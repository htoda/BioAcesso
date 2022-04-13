package br.com.bioconnect.BioAcesso.model.form;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.bioconnect.BioAcesso.model.User;

public class UserFormCadastroRemoto {

	@NotNull @NotEmpty
	private BigInteger userId;

	private String registration;
	
	private String name;
	
	private String password;
	
	private String salt;
	
	// grupo que o usuario será incluido
	@NotNull @NotEmpty
	private Integer groupId;
	
	// perfil que o usuario será incluido
	// 1 - admin
	private Integer roleId;
	
	// device no qual o usuario será incluido
	private String deviceId;
	private String deviceIp;
	private String deviceName;
	
    //atributos de controle
	private String turma;
    private String controle2;
    private String controle3;

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

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceIp() {
		return deviceIp;
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	
	public User converter(UserFormCadastroRemoto obj) {
		return new User(obj);
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
