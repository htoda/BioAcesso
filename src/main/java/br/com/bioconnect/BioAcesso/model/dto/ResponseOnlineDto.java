package br.com.bioconnect.BioAcesso.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseOnlineDto {
	private ResultOnlineDto result;
	
	public ResponseOnlineDto() {	
	}
	
	public ResponseOnlineDto(ResultOnlineDto result) {
		this.result = result;
	}

	public ResultOnlineDto getResult() {
		return result;
	}

	public void setResult(ResultOnlineDto result) {
		this.result = result;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String res = "";
		try {
			res = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			res = "{}";
		}
		//return "ResponseOnlineDto [result=" + res + "]";
		return res;
	}
	
	
	
}
