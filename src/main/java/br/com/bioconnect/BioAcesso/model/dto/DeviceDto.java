package br.com.bioconnect.BioAcesso.model.dto;

import org.springframework.data.domain.Page;

import br.com.bioconnect.BioAcesso.model.Device;

public class DeviceDto {

	private String id;

	private String name;

	private String ip;

	private String public_key;

	public DeviceDto(Device obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.ip = obj.getIp();
		this.public_key = obj.getPublicKey();
	}
	
	public static Page<DeviceDto> converter(Page<Device> lista) {
		return lista.map(DeviceDto::new);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIp() {
		return ip;
	}

	public String getPublic_key() {
		return public_key;
	}

	
}
