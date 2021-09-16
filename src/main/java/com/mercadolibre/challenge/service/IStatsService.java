/**
 * 
 */
package com.mercadolibre.challenge.service;

import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.domain.StatsDTO;

/**
 * Interfaz para estadisticas
 * 
 * @author dannny84@gmail.com
 *
 */
@Service
public interface IStatsService {

	/**
	 * Encargado de generar las estadisticas del proceso
	 * 
	 * @return {@link StatsDTO}
	 */
	public StatsDTO stats();
}
