package br.com.bioconnect.BioAcesso.Exception;

public class NewUserIdentifiedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NewUserIdentifiedException(String errorMessage) {
        super(errorMessage);
    }
		
}
