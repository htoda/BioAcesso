package br.com.bioconnect.BioAcesso.model.dtodevice;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

import br.com.bioconnect.BioAcesso.Exception.NewUserIdentifiedException;

public class NewUserIdentifiedDTO {

	private String device_id;
	private String identifier_id;
	private String time;
	private String user_has_image;
	private String user_id;
	private String user_name;
	private String portal_id;
	private String duress;
	private String event;
	private String face_mask;

	// allRequestParams = [{session=, card_value=0, device_id=4408801109217724,
	// duress=0, event=8,face_mask=0, identifier_id=1717658368, password=, portal_id=1, qrcode_value=,
	// time=1648466227, user_has_image=1, user_id=12, user_name=Hugo, uuid=}]

	public NewUserIdentifiedDTO(Map<String, String> allRequestParams) {
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
		
		/*
		allRequestParams.forEach((k, v) -> {
			if (k != null) {
				System.out.println("Key : " + k + ", Value : " + v);
			}
		});
		*/
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getIdentifier_id() {
		return identifier_id;
	}

	public void setIdentifier_id(String identifier_id) {
		this.identifier_id = identifier_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUser_has_image() {
		return user_has_image;
	}

	public void setUser_has_image(String user_has_image) {
		this.user_has_image = user_has_image;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPortalId() {
		return portal_id;
	}

	public void setPortalId(String portalId) {
		this.portal_id = portalId;
	}

	public String getDuress() {
		return duress;
	}

	public void setDuress(String duress) {
		this.duress = duress;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getFace_mask() {
		return face_mask;
	}

	public void setFace_mask(String face_mask) {
		this.face_mask = face_mask;
	}

	@Override
	public int hashCode() {
		return Objects.hash(device_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewUserIdentifiedDTO other = (NewUserIdentifiedDTO) obj;
		return Objects.equals(device_id, other.device_id);
	}

}
