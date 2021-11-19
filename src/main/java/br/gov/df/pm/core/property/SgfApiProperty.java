package br.gov.df.pm.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("pmdf")
public class SgfApiProperty {
	
	private String originPermitida = "http://localhost:8080";
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

}
