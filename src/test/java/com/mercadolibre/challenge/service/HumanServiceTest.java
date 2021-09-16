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
	
	@Test
	public void testIsMutant_whenWrongDna() {
		HumanDTO human = new HumanDTO();
		String[] dna = {"ATGCGA","CAGTGC","TTGT","AGAAGG","CCCCTA","TCACTG"};
		human.setDna(Arrays.asList(dna));
				
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.isMutant(human);
	    });

	    String expectedMessage = ApiConstant.WRONG_HUMAN_DNA;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testIsMutant_whenExceptionThrown() {
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.isMutant(null);
	    });

	    String expectedMessage = ApiConstant.WRONG_HUMAN;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
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
}
