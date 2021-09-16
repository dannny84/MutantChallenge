/**
 * 
 */
package com.mercadolibre.challenge.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mercadolibre.challenge.database.model.HumanEntity;

/**
 * Interfaz responsable del acceso de la entidad HumanEntity
 * 
 * @author dannny84@gmail.com
 *
 */
@Repository
public interface IHumanRepository extends CrudRepository<HumanEntity, Long>{

	/**
	 * 
	 * @param dna
	 * @param isMutant
	 * @return
	 */
	@Transactional(readOnly = true)
	public Optional<HumanEntity> findByDna(String dna);
	
	@Transactional(readOnly = true)
	public long countByIsMutant(boolean isMutant);
	
}
