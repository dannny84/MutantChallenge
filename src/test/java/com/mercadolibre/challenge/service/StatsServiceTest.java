/**
 * 
 */
package com.mercadolibre.challenge.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mercadolibre.challenge.domain.StatsDTO;
import com.mercadolibre.challenge.service.implementation.StatsService;

import lombok.NoArgsConstructor;

/**
 * Clase test para el service Stats
 * 
 * @author dannny84@gmail.com
 *
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StatsServiceTest {

	@InjectMocks
	private StatsService service;
	
	@Mock
	private IHumanService humanService;
	
	/**
	 * valida cuando no es mutante
	 */
	@Test
	public void testStats_whenOk() {
		Long totalHumans = 50l;
		Long totalMutants = 22l;
		when(humanService.count()).thenReturn(totalHumans);
		when(humanService.countByIsMutant(any(Boolean.class))).thenReturn(totalMutants);
		
		StatsDTO response = service.stats();
		
		float value = 0.44f;
		
		Assert.assertEquals(value, response.getRatio(), 0.0002);
	}
	
	/**
	 * Valida cuando no existen humanos 
	 */
	@Test
	public void testStats_whenThereIsNotHuman() {
		Long totalHumans = 0l;
		Long totalMutants = 0l;
		when(humanService.count()).thenReturn(totalHumans);
		when(humanService.countByIsMutant(any(Boolean.class))).thenReturn(totalMutants);
		
		StatsDTO response = service.stats();
		
		float value = 0.0f;
		
		Assert.assertEquals(value, response.getRatio(), 0.0002);
	}
}
