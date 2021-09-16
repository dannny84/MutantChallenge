/**
 * 
 */
package com.mercadolibre.challenge.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO con la informacion asociada a un humano
 * 
 * @author dannny84@gmail.com
 *
 */
@Getter
@Setter
@ApiModel(value = "Humano")
public class HumanDTO implements Serializable{

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("dna")
	private List<String> dna;
}
