/**
 * 
 */
package com.mercadolibre.challenge.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.database.model.HumanEntity;
import com.mercadolibre.challenge.database.repository.IHumanRepository;
import com.mercadolibre.challenge.domain.HumanDTO;
import com.mercadolibre.challenge.service.IHumanService;
import com.mercadolibre.challenge.util.ApiConstant;

/**
 * Servicio responsable de la logica de negocio de Humanos
 * 
 * @author dannny84@gmail.com
 *
 */
@Service
public class HumanService implements IHumanService {
	
	/**
	 * Inyeccion del repository
	 */
	@Autowired 
	private IHumanRepository repository;

	/*
	 * (non-Javadoc)
	 * @see com.mercadolibre.challenge.service.IMutantService#isMutant(com.mercadolibre.challenge.domain.HumanDTO)
	 */
	@Override
	public boolean isMutant(HumanDTO human) {
		
		validateHuman(human);
		
		Optional<HumanEntity> humanEntity = repository.findByDna(human.getDna().toString());
		boolean isMutant;
		
		if(humanEntity.isPresent()) {
			isMutant = humanEntity.get().isMutant();
		}else {
			//TODO - DEFINIR LA LOGICA DEL METODO
			isMutant= true;
			repository.save(new HumanEntity(human.getDna().toString(), isMutant));
		}

		return isMutant;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mercadolibre.challenge.service.IHumanService#countByIsMutant(boolean)
	 */
	@Override
	public long countByIsMutant(boolean isMutant) {
		return this.repository.countByIsMutant(isMutant);
	}

	/*
	 * (non-Javadoc)
	 * @see com.mercadolibre.challenge.service.IHumanService#count()
	 */
	@Override
	public long count() {
		return this.repository.count();
	}
	
	/**
	 * Valida la informacion contenidad en HumantDTO
	 * @param human
	 */
	private void validateHuman(HumanDTO human) {
		if(human == null) {
			throw new IllegalArgumentException(ApiConstant.WRONG_HUMAN);
		}
		
		if(human.getDna() == null) {
			throw new IllegalArgumentException(ApiConstant.WRONG_HUMAN_DNA);
		}
	}

}
