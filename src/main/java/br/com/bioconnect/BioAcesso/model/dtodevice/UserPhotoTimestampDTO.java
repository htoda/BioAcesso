package br.com.bioconnect.BioAcesso.model.dtodevice;

import java.math.BigInteger;
import java.util.Objects;

import br.com.bioconnect.BioAcesso.model.User;

public class UserPhotoTimestampDTO {
	private BigInteger user_id;
	private Long timestamp;

	public UserPhotoTimestampDTO(){
	}
	
	public UserPhotoTimestampDTO(BigInteger userId, Long timestamp) {
		super();
		this.user_id = userId;
		this.timestamp = timestamp;
	}

	public UserPhotoTimestampDTO(User usuario) {
		this.user_id = usuario.getUserId();
		this.timestamp = usuario.getImageTimestamp();
	}

	public BigInteger getUser_id() {
		return user_id;
	}

	public void setUser_id(BigInteger user_id) {
		this.user_id = user_id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPhotoTimestampDTO other = (UserPhotoTimestampDTO) obj;
		return Objects.equals(user_id, other.user_id);
	}

}
