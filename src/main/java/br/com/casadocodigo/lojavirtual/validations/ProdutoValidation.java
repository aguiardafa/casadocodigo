package br.com.casadocodigo.lojavirtual.validations;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.lojavirtual.models.Produto;

public class ProdutoValidation implements Validator {

	@Override
	public boolean supports(Class<?> classes) {
		return Produto.class.isAssignableFrom(classes);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// campos requeridos
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		// regra do numero de páginas
		Produto produto = (Produto) target;
		if(produto.getPaginas() <= 0 ) {
			errors.rejectValue("paginas", "field.required");
		}
	}
}
