/**
 * 
 */
package com.mercadolibre.challenge.service;

import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.domain.HumanDTO;

/**
 * Interfaz para Humanos 
 * 
 * @author dannny84@gmail.com
 *
 */
@Service
public interface IHumanService {

	/**
	 * Metodo responsable de la logica para determinar si una persona
	 * es mutante o no
	 * 
	 * @param human {@link HumanDTO}
	 * @return boolean "true" si la cadena de adn corresponde a un mutante, "false" en caso contrario
	 */
	public boolean isMutant(HumanDTO human);
	
	/**
	 * Consulta la cantidad de humanos que son mutantes
	 * 
	 * @param isMutant
	 * @return
	 */
	public long countByIsMutant(boolean isMutant);
	
	/**
	 * Consulta todos los humanos que han sido evaluados
	 * 
	 * @return
	 */
	public long count();
}
