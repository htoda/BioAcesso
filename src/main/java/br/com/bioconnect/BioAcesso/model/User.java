package br.com.bioconnect.BioAcesso.model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import br.com.bioconnect.BioAcesso.model.dto.UserDeviceDto;
import br.com.bioconnect.BioAcesso.model.form.UserForm;
import br.com.bioconnect.BioAcesso.model.form.UserFormCadastroRemoto;

@Entity(name = "users")
public class User {

	@Id
	@SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
	@Column(name = "user_id", updatable = false)
	private BigInteger userId;

	@Column
	private String registration;

	@Column
	private String name;

	@Column
	private String password;

	@Column
	private String salt;

	@Column
	private Long expires;

	// 0: padrao
	// 1: visitante
	@Column(name = "user_type_id")
	private Integer userTypeId;

	@Column(name = "begin_time")
	private Long beginTime;

	@Column(name = "end_time")
	private Long endTime;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	@Column(name = "foto")
	private byte[] foto;

	@Column(name = "image_timestamp")
	private Long imageTimestamp;

	@Column(name = "data_criacao")
	@CreationTimestamp
	private Timestamp dataCriacao;

	@Column(name = "data_atualizacao")
	private Timestamp dataAtualizacao;

	@ManyToMany
	@JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> listGroups;

	@OneToMany(mappedBy = "usuario")
	private Set<UserRoles> listRoles;

	@Column(name = "_turma")
	private String turma;

	@Column(name = "_controle2")
	private String controle2;

	@Column(name = "_controle3")
	private String controle3;

	public User() {
	}

	public User(BigInteger userId) {
		super();
		this.userId = userId;
	}
	
	public User(BigInteger user_id, Long imageTimestamp) {
		this.userId = user_id;
		this.imageTimestamp = imageTimestamp;
	}

	public User(UserFormCadastroRemoto form) {
		this.userId = form.getUserId();
		this.registration = form.getRegistration();
		this.name = form.getName();
		this.password = form.getPassword();
		this.salt = form.getSalt();
		listGroups = new HashSet<Group>();
		listGroups.add(new Group(form.getGroupId()));
		this.turma = form.getTurma();
		this.controle2 = form.getControle2();
		this.controle3 = form.getControle3();
	}

	public User(UserDeviceDto objDeviceDTO) {
		this.userId = objDeviceDTO.getId();
		this.registration = objDeviceDTO.getRegistration();
		this.name = objDeviceDTO.getName();
		this.password = objDeviceDTO.getPassword();
		this.salt = objDeviceDTO.getSalt();
		this.expires = objDeviceDTO.getExpires();
		this.beginTime = objDeviceDTO.getBegin_time();
		this.endTime = objDeviceDTO.getEnd_time();
		this.imageTimestamp = objDeviceDTO.getImage_timestamp();
		// grupo default
		this.setListGroups(new HashSet<>(Arrays.asList(new Group(1))));
	}

	public User(UserForm obj) {
		this.userId        = obj.getUserId();
		this.registration  = obj.getRegistration();
		this.name          = obj.getName();
		this.password      = obj.getPassword();
		this.salt          = obj.getSalt();
		this.expires       = obj.getExpires();
		this.userTypeId    = obj.getUserTypeId();
		this.beginTime     = obj.getBeginTime();
		this.endTime       = obj.getEndTime();
		this.foto          = obj.getFoto();
		this.imageTimestamp= obj.getImageTimestamp();
		this.listGroups    = new HashSet<Group>(Arrays.asList(new Group(obj.getGroupId())));
		this.listRoles     = new HashSet<UserRoles>(Arrays.asList(new UserRoles(obj.getRoleId())));
		this.turma         = obj.getTurma();
		this.controle2     = obj.getControle2();
		this.controle3     = obj.getControle3();
	}
	
	/*
	public User(UserForm obj) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
		field.setAccessible(true);
			try {
				field.set(this, allRequestParams.get(field.getName()));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new NewUserIdentifiedException("Erro no mapeamento do objeto de retorno da da classe NewUserIdentified");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new NewUserIdentifiedException("Erro no mapeamento do objeto de retorno da da classe NewUserIdentified");
			}
	}
	 */
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

	public Set<Group> getListGroups() {
		return listGroups;
	}

	public void setListGroups(Set<Group> listGroups) {
		this.listGroups = listGroups;
	}

	public Set<UserRoles> getListRoles() {
		return listRoles;
	}

	public void setListRoles(Set<UserRoles> listRoles) {
		this.listRoles = listRoles;
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

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(userId, other.userId);
	}

	public User copyNewValues(UserForm obj) {
		this.registration  = obj.getRegistration();
		this.name          = obj.getName();
		this.password      = obj.getPassword();
		this.salt          = obj.getSalt();
		this.expires       = obj.getExpires();
		this.userTypeId    = obj.getUserTypeId();
		this.beginTime     = obj.getBeginTime();
		this.endTime       = obj.getEndTime();
		this.foto          = obj.getFoto();
		this.imageTimestamp= obj.getImageTimestamp();
		this.listGroups    = new HashSet<Group>(Arrays.asList(new Group(obj.getGroupId())));
		this.listRoles     = new HashSet<UserRoles>(Arrays.asList(new UserRoles(obj.getRoleId())));
		this.turma         = obj.getTurma();
		this.controle2     = obj.getControle2();
		this.controle3     = obj.getControle3();
		return this;
	}

}
