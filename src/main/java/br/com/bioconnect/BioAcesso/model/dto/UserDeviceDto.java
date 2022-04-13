package br.com.bioconnect.BioAcesso.model.dto;

import java.math.BigInteger;

public class UserDeviceDto {
	private BigInteger id;

	private String registration;
	
	private String name;

	private String password;

	private String salt;
	
	private Long expires;
	
	private Long image_timestamp;
	
	private Long begin_time;
	
	private Long end_time;
	
	private Integer user_type_id;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public Long getImage_timestamp() {
		return image_timestamp;
	}

	public void setImage_timestamp(Long image_timestamp) {
		this.image_timestamp = image_timestamp;
	}

	public Long getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Long begin_time) {
		this.begin_time = begin_time;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

	public Integer getUser_type_id() {
		return user_type_id;
	}

	public void setUser_type_id(Integer user_type_id) {
		this.user_type_id = user_type_id;
	}
	
}
