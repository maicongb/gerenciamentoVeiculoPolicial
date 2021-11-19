package br.gov.df.pm.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { AnoModeloValidator.class })
public @interface AnoModelo {
	
	String message() default "O ano do modelo não pode ser inferior ou superior a um ano da data de fabricação";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String anoModeloField();
	
	String anoFabricacaoField();

}
