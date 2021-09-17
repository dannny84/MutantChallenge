package com.mercadolibre.challenge.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.challenge.domain.StatsDTO;
import com.mercadolibre.challenge.service.IStatsService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
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

	private final Bucket bucket;
	
	/**
	 * Servicio
	 */
	@Autowired
	private IStatsService service;
	
	@Autowired
	public StatsController(@Value("${security.tokens.max}") int tokensAllowed, @Value("${security.tokens.duration}") int tokensDuration) {
        Bandwidth limit = Bandwidth.classic(tokensAllowed, Refill.greedy(tokensAllowed, Duration.ofSeconds(tokensDuration)));
        this.bucket = Bucket4j.builder()
            .addLimit(limit)
            .build();
    }
	
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
		if (bucket.tryConsume(1)) {
			return new ResponseEntity<>(service.stats(), HttpStatus.OK);
		}else {
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
	}
}
