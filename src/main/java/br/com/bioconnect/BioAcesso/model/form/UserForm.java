package br.com.bioconnect.BioAcesso.model.form;

import java.math.BigInteger;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.bioconnect.BioAcesso.annotations.TelefoneValidator;
import br.com.bioconnect.BioAcesso.model.User;

public class UserForm {

	private BigInteger userId;
	
	@NotNull(message = "Matrícula/Identificação do usuário não pode ser nula")
	@NotEmpty(message = "Matrícula/Identificação do usuário deve ter conteúdo informado")
	private String registration;
	
	@NotNull(message = "Nome não pode ser nulo")
	@NotEmpty(message = "Nome deve ter conteúdo informado")
	private String name;

	private String password;

	private String salt;
	
	private Long expires;
	
	@NotNull(message = "Tipo do usuário não pode ser nulo (0-usuário padrão; 1- usuário visitante")
	private Integer userTypeId;
	
	private Long beginTime;
	
	private Long endTime;
	
    private byte[] foto;
	
	//private Long imageTimestamp;
	
    @NotNull(message = "Grupo do usuário deve ser informado (1:usuário padrão")
    @Min(value = 1, message = "Perfil do usuário deve ser selecionado entre 1 e 1 (usuário padrão)")
    @Max(value = 1, message = "Perfil do usuário deve ser selecionado entre 1 e 1 (usuário padrão)")
    private Integer groupId;
    
    @NotNull(message = "Perfil do usuário deve ser informado (0:usuário padrão; 1:usuário administrador")
    @Min(value = 0, message = "Perfil do usuário deve ser selecionado entre 0 (usuário padrão) e 1 (usuário administrador)")
    @Max(value = 1, message = "Perfil do usuário deve ser selecionado entre 0 (usuário padrão) e 1 (usuário administrador)")
	private Integer roleId;
	
	private String turma;
	
	@TelefoneValidator
	private String telefone;
    
	@Email(message = "Email deve ser válido")
	private String email;
	
	
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

	/*
	 * public Long getImageTimestamp() { return imageTimestamp; }
	 * 
	 * public void setImageTimestamp(Long imageTimestamp) { this.imageTimestamp =
	 * imageTimestamp; }
	 */

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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
