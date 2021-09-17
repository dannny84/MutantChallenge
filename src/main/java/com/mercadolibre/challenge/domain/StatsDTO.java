/**
 * 
 */
package com.mercadolibre.challenge.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO con la informacion de las estadisticas
 * 
 * @author dannny84@gmail.com
 *
 */
@Getter
@Setter
@ApiModel(value="Estadisticas")
public class StatsDTO implements Serializable{

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("count_mutant_dna")
	private long countMutantDna;
	
	@JsonProperty("count_human_dna")
	private long countHumanDna;
	
	@JsonProperty("ratio")
	private float ratio;
}
