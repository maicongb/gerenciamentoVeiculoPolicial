package br.gov.df.pm.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { PlacaValidator.class })
public @interface Placa {
	
	String message() default "Placa deve ter os seguintes padrões: XXX9999 ou XXX-9X99 para carro ou XXX99X9 para motocicleta";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String placaField();

}
