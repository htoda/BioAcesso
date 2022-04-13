package br.com.bioconnect.BioAcesso.model;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class MessageBroadcast {
	
    @Id
    @SequenceGenerator(name="message_broadcast_seq",sequenceName="message_broadcast_seq",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="message_broadcast_seq")
	@Column(updatable=false)
    private Integer id;
	
    @Column
	private String deviceId;
    
    @Column
	private BigInteger userId;
    
	@Column
	private String tipoOperacao;
	
	@Column (name = "data_criacao")
	@CreationTimestamp
	private Timestamp dataCriacao;
	
	@Column
	private Long timestampOperation;
	
	@Column
	private Boolean processado = false;
	
	public MessageBroadcast() {
	}
	
	public MessageBroadcast(String deviceId, BigInteger userId, String tipo, Long timestampOperation) {
		super();
		this.deviceId = deviceId;
		this.userId = userId;
		this.tipoOperacao = tipo;
		this.timestampOperation = timestampOperation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getTipo() {
		return tipoOperacao;
	}

	public void setTipo(String tipo) {
		this.tipoOperacao = tipo;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Long getTimestampOperation() {
		return timestampOperation;
	}

	public void setTimestampOperation(Long timestampOperation) {
		this.timestampOperation = timestampOperation;
	}

	public Boolean getProcessado() {
		return processado;
	}

	public void setProcessado(Boolean processado) {
		this.processado = processado;
	}

	
}
	
	
	
	
	
