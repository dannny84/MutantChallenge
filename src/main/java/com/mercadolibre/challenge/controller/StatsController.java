package com.mercadolibre.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.challenge.domain.StatsDTO;
import com.mercadolibre.challenge.service.IStatsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase responsable de las capacidades de las estadisticas. Esta clase
 * implementa @RestController.
 * 
 * @author dannny84@gmail.com
 *
 */
@RestController
@RequestMapping("/stats")
public class StatsController {

	/**
	 * Servicio
	 */
	@Autowired
	private IStatsService service;
	
	/**
	 * Genera las estadisticas del servicio de deteccion de Mutantes, a partir de la informacion
	 * almacenada en la base de datos.
	 * 
	 * @return StatsDTO
	 */
	@ApiOperation(value = "Genera las estadisticas del servicio de deteccion de Mutantes")
	@GetMapping
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Estadistica del servicio de deteccion de Mutantes") })
	public @ResponseBody ResponseEntity<StatsDTO> stats() {
		
		return new ResponseEntity<>(service.stats(), HttpStatus.OK);
	}
}
