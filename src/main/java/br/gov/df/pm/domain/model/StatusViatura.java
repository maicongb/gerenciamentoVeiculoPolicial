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
@Entity(name = "STATUSVIATURA")
public class StatusViatura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "svi_Codigo")
    private Long id;
    
    @Column(name = "svi_Nome", nullable = false)
    private String nome;
    
    @Column(name = "svi_Ativo", nullable = false)
    private Integer ativo = 1;

}
