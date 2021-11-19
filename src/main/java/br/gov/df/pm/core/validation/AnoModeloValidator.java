package br.gov.df.pm.core.validation;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class AnoModeloValidator implements ConstraintValidator<AnoModelo, Object> {
	
	private String anoModeloField;
	private String anoFabricacaoField;

	@Override
	public void initialize(AnoModelo constraint) {
		this.anoFabricacaoField = constraint.anoFabricacaoField();
		this.anoModeloField = constraint.anoModeloField();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		//RECUPERA O ATRIBUTO anoModelo DA CLASSE ViaturaResumoInput
		try {
			LocalDate anoModelo = (LocalDate) BeanUtils.getPropertyDescriptor(
								objetoValidacao.getClass(), anoModeloField)
								.getReadMethod().invoke(objetoValidacao);
			
			LocalDate anoFabricacao = (LocalDate) BeanUtils.getPropertyDescriptor(
					objetoValidacao.getClass(), anoFabricacaoField)
					.getReadMethod().invoke(objetoValidacao);
			
			Period periodo = Period.between(anoFabricacao,anoModelo);
			
			if(periodo.getYears() < 0 || periodo.getYears() > 1) {
				valido = false;
			}
			
			return valido;
			
		} catch (Exception e) {
			throw new ValidationException(e);
		}
		

	}

}
