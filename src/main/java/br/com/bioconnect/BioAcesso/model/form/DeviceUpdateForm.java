package br.com.bioconnect.BioAcesso.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.repository.IDeviceRepository;

public class DeviceUpdateForm {
	
	@NotNull @NotEmpty @Length(min = 3)
	private String name;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String ip;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String publicKey;

	public String getName() {
		return name;
	}

	public String getIp() {
		return ip;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public Device converter(DeviceUpdateForm obj) {
		return new Device(obj);
	}

	public Device atualizar(String id, IDeviceRepository objRepository) {
		Device obj = objRepository.getOne(id);
		obj.setName(this.name);
		obj.setIp(this.ip);
		obj.setPublicKey(this.publicKey);
		return obj;
	}

}
