package br.gov.df.pm.api.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaturaUpmResumoModal {

	private LocalDate dataEntrada;
	private UpmModal upm;
	
}
