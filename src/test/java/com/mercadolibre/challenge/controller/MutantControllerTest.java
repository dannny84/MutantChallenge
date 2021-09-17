/**
 * 
 */
package com.mercadolibre.challenge.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
	private MutantController controller = new MutantController(1, 1);
	
	/**
	 * Service
	 */
	@Mock
	private IHumanService service;
	
	/**
	 * Valida cuando es un mutante
	 */
	@Test
	public void testIsMutant_whenOk() {
		when(service.isMutant(any(HumanDTO.class))).thenReturn(true);
		
		ResponseEntity<Void> responseController = controller.isMutant(getHumanDTO());
		
		assertEquals(HttpStatus.OK, responseController.getStatusCode());
	}
	
	/**
	 * Valida cuando no es un mutante
	 */
	@Test
	public void testIsMutant_whenForbidden() {
		when(service.isMutant(any(HumanDTO.class))).thenReturn(false);
		
		ResponseEntity<Void> responseController = controller.isMutant(getHumanDTO());
		
		assertEquals(HttpStatus.FORBIDDEN, responseController.getStatusCode());
	}
	
	/**
	 * Valida cuando el dna es null
	 */
	@Test
	public void testIsMutant_whenBadRequest_dnaNull() {
		HumanDTO human = getHumanDTO();
		human.setDna(null);
		ResponseEntity<Void> responseController = controller.isMutant(human);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseController.getStatusCode());
	}
	
	/**
	 * Valida cuando el request es null
	 */
	@Test
	public void testIsMutant_whenBadRequest_bodyNull() {
		ResponseEntity<Void> responseController = controller.isMutant(null);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseController.getStatusCode());
	}
	
	/**
	 * Valida cuando hay demasiadas solicitudes al servicio
	 */
	@Test
	public void testIsMutant_whenTooManyRequest() {
		when(service.isMutant(any(HumanDTO.class))).thenReturn(false);
		
		ResponseEntity<Void> responseController = controller.isMutant(getHumanDTO());
		responseController = controller.isMutant(getHumanDTO());
		responseController = controller.isMutant(getHumanDTO());
		
		assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseController.getStatusCode());
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
