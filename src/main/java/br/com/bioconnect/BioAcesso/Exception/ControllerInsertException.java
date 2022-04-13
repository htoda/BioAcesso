package br.com.bioconnect.BioAcesso.Exception;

public class ControllerInsertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ControllerInsertException(String errorMessage) {
        super(errorMessage);
    }
		
}
