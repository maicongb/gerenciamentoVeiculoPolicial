package br.gov.df.pm.core.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

/*
 * VALIDA PLACA COMUM, MERCOSUL DE CARRO E MOTO
 */

public class PlacaValidator implements ConstraintValidator<Placa, Object>{

	private String placaField;
	
	@Override
	public void initialize(Placa constraint) {
		this.placaField = constraint.placaField();
	}
	
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		try {
			String placa = (String) BeanUtils.getPropertyDescriptor(
					objetoValidacao.getClass(), placaField)
					.getReadMethod().invoke(objetoValidacao);
			
			Pattern pattern;
			
			pattern = Pattern
		            .compile("[a-zA-Z]{3}[0-9]{4}|[a-zA-Z]{3}[0-9]{1}[a-zA-Z]{1}[0-9]{2}"
		            		+ "|[a-zA-Z]{3}[0-9]{2}[a-zA-Z]{1}[0-9]{1}");
			Matcher matPlacaComum = pattern.matcher(placa);
			valido = matPlacaComum.matches();

			return valido;
			
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

}
