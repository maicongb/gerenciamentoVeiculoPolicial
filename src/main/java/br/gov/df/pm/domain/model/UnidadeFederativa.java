package br.gov.df.pm.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "UFE")
public class UnidadeFederativa {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@EqualsAndHashCode.Include
	    @Column(name = "ufe_Codigo")
	    private Long id;
	    
	    @Column(name = "ufe_Nome")
	    private String nome;
	    
	    @Column(name = "ufe_Sigla")
	    private String sigla;
	    
	    @Column(name = "ufe_Ativo")
	    private Integer ativo;

}
