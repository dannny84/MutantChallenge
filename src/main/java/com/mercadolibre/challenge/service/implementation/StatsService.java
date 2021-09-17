/**
 * 
 */
package com.mercadolibre.challenge.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.domain.StatsDTO;
import com.mercadolibre.challenge.service.IHumanService;
import com.mercadolibre.challenge.service.IStatsService;

/**
 *  Servicio responsable de la logica de las estadisticas
 *  
 * @author dannny84@gmail.com
 *
 */
@Service
public class StatsService implements IStatsService{

	/**
	 * Inyeccion del service human
	 */
	@Autowired
	private IHumanService humanService;
	
	/*
	 * (non-Javadoc)
	 * @see com.mercadolibre.challenge.service.IStatsService#stats()
	 */
	@Override
	public StatsDTO stats() {
		
		Long totalHumans = humanService.count();
		Long totalMutants = humanService.countByIsMutant(true);
		
		StatsDTO stats = new StatsDTO();
		stats.setCountHumanDna(totalHumans);
		stats.setCountMutantDna(totalMutants);
		
		if(totalHumans > 0) {
			stats.setRatio(totalMutants.doubleValue()/totalHumans.doubleValue());
		}else {
			stats.setRatio(0);
		}
		
		return stats;
	}

}
