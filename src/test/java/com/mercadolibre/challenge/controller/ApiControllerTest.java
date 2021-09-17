/**
 * 
 */
package com.mercadolibre.challenge.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.NoArgsConstructor;

/**
 * Clase Test para el controlador del api
 * 
 * @author dannny84@gmail.com
 *
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ApiControllerTest {

	/***
	 * Controlador principal 
	 */
	@InjectMocks
	private ApiController controller = new ApiController(1, 1);
	
	/**
	 * Valida cuando la respuesta es OK
	 */
	@Test
	public void testIsMutant_whenOk() {
		ResponseEntity<String> responseController = controller.version();

		assertEquals(HttpStatus.OK, responseController.getStatusCode());
	}
	
	/**
	 * Valida cuando hay demasiadas solicitudes al servicio
	 */
	@Test
	public void testStats_whenTooManyRequest() {
		ResponseEntity<String> responseController = controller.version();
		responseController = controller.version();
		responseController = controller.version();
		
		assertEquals(HttpStatus.TOO_MANY_REQUESTS, responseController.getStatusCode());
	}
}
