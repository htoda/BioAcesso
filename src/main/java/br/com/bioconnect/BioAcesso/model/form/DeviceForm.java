package br.com.bioconnect.BioAcesso.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;

public class DeviceForm {

	@NotNull
	private String id;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String name;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String ip;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String publicKey;
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIp() {
		return ip;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public Device converter(DeviceForm obj) {
		return new Device(obj);
	}

	public Device atualizar(String id, IDeviceRepository objRepository) {
		Device obj = objRepository.getOne(id);
		//obj.setId(obj.getId());		
		obj.setName(this.name);
		obj.setIp(this.ip);
		obj.setPublicKey(this.publicKey);
		return obj;
	}

}
