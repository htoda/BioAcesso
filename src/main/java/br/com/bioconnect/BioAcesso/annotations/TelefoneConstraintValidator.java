package br.com.bioconnect.BioAcesso.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelefoneConstraintValidator implements ConstraintValidator<TelefoneValidator, String> {

	@Override
	public void initialize(TelefoneValidator contactNumber) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return contactField != null && contactField.matches("[0-9]+") && (contactField.length() > 10)
				&& (contactField.length() < 11);
	}

}