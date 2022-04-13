package br.com.bioconnect.BioAcesso.Exception;

public class ErroEnvioMensagemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErroEnvioMensagemException(String errorMessage) {
        super(errorMessage);
    }
		
}
