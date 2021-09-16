/**
 * 
 */
package com.mercadolibre.challenge.database.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase de persistencia para la tabla CH_HUMAN
 * 
 * @author dannny84@gmail.com
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "CH_HUMAN")
public class HumanEntity implements Serializable {

	/**
	 * Serial Id
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Getter
	@Setter
	@Column(name = "DNA", nullable = false, length = 2000)
	private String dna;
	
	@Getter
	@Setter
	@Column(name = "IS_MUTANT", nullable = false)
	private boolean isMutant;

	/**
	 * Constructor
	 * 
	 * @param dna
	 * @param isMutant
	 */
	public HumanEntity(String dna, boolean isMutant) {
		super();
		this.dna = dna;
		this.isMutant = isMutant;
	}
}
