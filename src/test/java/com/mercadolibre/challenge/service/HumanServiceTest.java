/**
 * 
 */
package com.mercadolibre.challenge.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mercadolibre.challenge.database.model.HumanEntity;
import com.mercadolibre.challenge.database.repository.IHumanRepository;
import com.mercadolibre.challenge.domain.HumanDTO;
import com.mercadolibre.challenge.service.implementation.HumanService;
import com.mercadolibre.challenge.util.ApiConstant;

import lombok.NoArgsConstructor;

/**
 * Clase test para el service Human
 * 
 * @author dannny84@gmail.com
 *
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class HumanServiceTest {

	@InjectMocks
	private HumanService service;
	
	@Mock
	private IHumanRepository repository;
	
	/**
	 * valida cuando no es mutante
	 */
	@Test
	public void testIsMutant_whenNotIsMutant() {
		
		Optional<HumanEntity> opt = Optional.ofNullable(null);
		when(repository.findByDna(any(String.class))).thenReturn(opt);
		
		final HumanDTO human = new HumanDTO();
		
		String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAATG","CGCCTA","TCACTG"};
		human.setDna(Arrays.asList(dna));
		
		boolean response = service.isMutant(human);
		
		assertFalse(response);
	}
	
	/**
	 * Valida cuando es mutante
	 */
	@Test
	public void testIsMutant_whenIsMutant() {
		
		Optional<HumanEntity> opt = Optional.ofNullable(null);
		when(repository.findByDna(any(String.class))).thenReturn(opt);
		
		HumanDTO human = new HumanDTO();
		String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		human.setDna(Arrays.asList(dna));
		
		boolean response = service.isMutant(human);
		
		assertTrue(response);
	}
	
	/**
	 * Valida cuando el dna es erroneo
	 */
	@Test
	public void testIsMutant_whenWrongDna() {
		HumanDTO human = new HumanDTO();
		String[] dna = {"ATGCGA","CAGTGC","TTGT","AGAAGG","CCTCTA"};
		human.setDna(Arrays.asList(dna));
				
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.isMutant(human);
	    });

	    String expectedMessage = ApiConstant.WRONG_SEQUENCE_DNA;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	/**
	 * Valida cuando el cuerpo del mensaje viene null
	 */
	@Test
	public void testIsMutant_whenExceptionThrown() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.isMutant(null);
	    });

	    String expectedMessage = ApiConstant.WRONG_HUMAN;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	/**
	 * Valida cuando el dna viene null
	 */
	@Test
	public void testIsMutant_whenDnaNull() {
		HumanDTO human = new HumanDTO();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.isMutant(human);
	    });

	    String expectedMessage = ApiConstant.WRONG_HUMAN_DNA;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	/**
	 * Valida cuando el mutante ya fue validado
	 */
	@Test
	public void testIsMutant_whenMutantExists() {
		HumanEntity entity = new HumanEntity();
		entity.setMutant(true);
		
		Optional<HumanEntity> humanEntity = Optional.of(entity);

		when(repository.findByDna(any(String.class))).thenReturn(humanEntity);
		
		final HumanDTO human = new HumanDTO();
		
		String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAATG","CGCCTA","TCACTG"};
		human.setDna(Arrays.asList(dna));
		
		boolean response = service.isMutant(human);
		
		assertTrue(response);
	}
}
