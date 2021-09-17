/**
 * 
 */
package com.mercadolibre.challenge.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mercadolibre.challenge.domain.StatsDTO;
import com.mercadolibre.challenge.service.IStatsService;

import lombok.NoArgsConstructor;

/**
 * Clase Test para el controlador Stats
 * 
 * @author dannny84@gmail.com
 *
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StatsControllerTest {

	/***
	 * Controlador principal 
	 */
	@InjectMocks
	private StatsController controller = new StatsController(1, 1);
	
	/**
	 * Service
	 */
	@Mock
	private IStatsService service;
	
	/**
	 * Valida cuando la respuesta es Ok
	 */
	@Test
	public void testStats_whenOk() {
		when(service.stats()).thenReturn(new StatsDTO());
		
		ResponseEntity<StatsDTO> responseController = controller.stats();
		
		assertEquals(HttpStatus.OK, responseController.getStatusCode());
	}
	
	/**
	 * Valida cuando hay demasiadas solicitudes al servicio
	 */
	@Test
	public void testStats_whenTooManyRequest() {
		when(service.stats()).thenReturn(new StatsDTO());
		
		ResponseEntity<StatsDTO> responseController = controller.stats();
		responseController = controller.stats();
		responseController = controller.stats();
		
		assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseController.getStatusCode());
	}
}
