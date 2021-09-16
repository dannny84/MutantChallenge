/**
 * 
 */
package com.mercadolibre.challenge.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
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

import com.google.gson.Gson;
import com.mercadolibre.challenge.domain.HumanDTO;
import com.mercadolibre.challenge.service.IHumanService;

import lombok.NoArgsConstructor;

/**
 * Clase Test para el controlador Mutant
 * 
 * @author dannny84@gmail.com
 *
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class MutantControllerTest {
	
	/***
	 * Controlador principal 
	 */
	@InjectMocks
	private MutantController controller;
	
	/**
	 * Service
	 */
	@Mock
	private IHumanService service;
	
	
	@Test
	public void testIsMutantOk() {
		
		when(service.isMutant(any(HumanDTO.class))).thenReturn(true);
		
		ResponseEntity<Void> responseController = controller.isMutant(getHumanDTO());
		
		assertEquals(HttpStatus.OK, responseController.getStatusCode());
	}
	
	@Test
	public void testIsMutantForbidden() {
		
		when(service.isMutant(any(HumanDTO.class))).thenReturn(false);
		
		ResponseEntity<Void> responseController = controller.isMutant(getHumanDTO());
		
		assertEquals(HttpStatus.FORBIDDEN, responseController.getStatusCode());
	}
	
	@Test
	public void testIsMutantBadRequest() {
		
		RuntimeException exception = new IllegalArgumentException("");
		doThrow(exception).when(service).isMutant(eq(null));
		
		ResponseEntity<Void> responseController = controller.isMutant(null);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseController.getStatusCode());
	}
	
	/**
	 * Builder human dto
	 * 
	 * @return object to json
	 */
	private HumanDTO getHumanDTO() {
		final StringBuilder json = new StringBuilder("{")
				.append("\"dna\": [\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]")
				.append("}");

		return new Gson().fromJson(json.toString(), HumanDTO.class);
	}
}
