package br.com.bioconnect.BioAcesso.Exception;

public class ControllerDuplicateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ControllerDuplicateException(String errorMessage) {
        super(errorMessage);
    }
		
}
