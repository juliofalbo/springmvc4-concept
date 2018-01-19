package org.springconcept.validators;

import org.springconcept.models.Usuario;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//Classe que implementa a interface de validação do Spring
public class UsuarioValidator implements Validator {

	// Método responsável por informar ao Spring qual entidade ele vai validar
	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	// Validações
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "email", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senha", "field.required");

		Usuario usuario = (Usuario) target;

		if (usuario.getSenha().length() <= 0) {
			errors.rejectValue("senha", "field.required");
		}

	}

}
