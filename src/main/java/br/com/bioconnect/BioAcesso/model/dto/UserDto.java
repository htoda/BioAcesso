package br.com.bioconnect.BioAcesso.model.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.bioconnect.BioAcesso.model.User;

public class UserDto {

	private BigInteger userId;

	private String registration;
	
	private String name;

	private String password;

	private String salt;
	
	private Long expires;
	
	private Integer userTypeId;
	
	private Long beginTime;
	
	private Long endTime;
	
    private byte[] foto;
	
	private Long imageTimestamp;
	
	private Timestamp dataCriacao;
	
	private Timestamp dataAtualizacao;

    private Set<GroupDTO> listGroups;
    
	private Set<UserRolesDTO> listRoles;
	
	private String turma;

	private String telefone;
    
	private String email;

	
	public UserDto(User obj) {
		this.userId = obj.getUserId();
		this.registration = obj.getRegistration();
		this.name = obj.getName();
		this.password = obj.getPassword();
		this.salt = obj.getSalt();
		this.expires = obj.getExpires();
		this.userTypeId = obj.getUserTypeId();
		this.beginTime = obj.getBeginTime();
		this.endTime = obj.getEndTime();
		this.foto = obj.getFoto();
		this.imageTimestamp = obj.getImageTimestamp();
		this.dataCriacao = obj.getDataCriacao();
		this.dataAtualizacao = obj.getDataAtualizacao();
		this.listGroups = obj.getListGroups().stream().map(l -> new GroupDTO(l)).collect(Collectors.toSet());
		this.listRoles = obj.getListRoles().stream().map(l -> new UserRolesDTO(l)).collect(Collectors.toSet());
		this.turma = obj.getTurma();
		this.telefone = obj.getTelefone();
		this.email = obj.getEmail();
	}
	
	public static Page<UserDto> converter(Page<User> lista) {
		return lista.map(UserDto::new);
	}

	public static List<UserDto> converter(List<User> lista) {
		return lista.stream().map(l -> new UserDto(l)).collect(Collectors.toList());
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

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Timestamp getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Timestamp dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Set<GroupDTO> getListGroups() {
		return listGroups;
	}

	public void setListGroups(Set<GroupDTO> listGroups) {
		this.listGroups = listGroups;
	}

	public Set<UserRolesDTO> getListRoles() {
		return listRoles;
	}

	public void setListRoles(Set<UserRolesDTO> listRoles) {
		this.listRoles = listRoles;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getControle2() {
		return telefone;
	}

	public void setControle2(String controle2) {
		this.telefone = controle2;
	}

	public String getControle3() {
		return email;
	}

	public void setControle3(String controle3) {
		this.email = controle3;
	}
	
}
