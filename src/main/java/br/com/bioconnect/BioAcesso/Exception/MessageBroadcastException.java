package br.com.bioconnect.BioAcesso.Exception;

public class MessageBroadcastException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MessageBroadcastException(String errorMessage) {
        super(errorMessage);
    }
		
}
