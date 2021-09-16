package com.mercadolibre.challenge.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.challenge.domain.HumanDTO;
import com.mercadolibre.challenge.service.IHumanService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase responsable de las capacidades de los mutantes. Esta clase
 * implementa @RestController.
 * 
 * @author dannny84@gmail.com
 *
 */
@RestController
@RequestMapping("/mutant/")
public class MutantController {

	Logger logger = Logger.getLogger(MutantController.class);
	
	/**
	 * Servicio
	 */
	@Autowired
	private IHumanService service;
	
	/**
	 * Detecta si un humano es mutante segun la secuencia de ADN recibida
	 * por parametro  
	 *
	 * @param HumanDTO
	 * @return boolean "true" si la cadena de adn corresponde a un mutante, "false" en caso contrario
	 */
	@ApiOperation(value = "Detecta si un humano es mutante segun la secuencia de ADN")
	@PostMapping
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 403, message = "Forbidden") })
	public @ResponseBody ResponseEntity<Void> isMutant(@ApiParam(value="Este es el adn") @RequestBody(required = true) HumanDTO humanDTO) {
		boolean response = false;
		try {
		logger.info("Detecta si un humano es mutante segun la secuencia de ADN");
		
		response = service.isMutant(humanDTO);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return response ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
