package org.springconcept.validators;

import org.springconcept.models.Produto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//Classe que implementa a interface de validação do Spring
public class ProdutoValidator implements Validator {

	// Método responsável por informar ao Spring qual entidade ele vai validar
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	// Validações
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");

		Produto produto = (Produto) target;

		if (produto.getPaginas() <= 0) {
			errors.rejectValue("paginas", "field.required");
		}

	}

}
