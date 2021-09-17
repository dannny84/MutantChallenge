package com.mercadolibre.challenge.controller;

import java.time.Duration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.challenge.domain.HumanDTO;
import com.mercadolibre.challenge.service.IHumanService;
import com.mercadolibre.challenge.util.ApiConstant;
import com.mercadolibre.challenge.util.PatternUtil;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
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
	 * Controla la cantidad de peticiones al servicio
	 */
	private final Bucket bucket;

	/**
	 * Servicio
	 */
	@Autowired
	private IHumanService service;
	
	/**
	 * Constructor
	 * @param tokensAllowed
	 * @param tokensDuration
	 */
	@Autowired
	public MutantController(@Value("${security.tokens.max}") int tokensAllowed, @Value("${security.tokens.duration}") int tokensDuration) {
        Bandwidth limit = Bandwidth.classic(tokensAllowed, Refill.greedy(tokensAllowed, Duration.ofSeconds(tokensDuration)));
        this.bucket = Bucket4j.builder()
            .addLimit(limit)
            .build();
    }
	
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
		ResponseEntity<Void> response;

		logger.info("Detecta si un humano es mutante segun la secuencia de ADN");
		
		if (bucket.tryConsume(1)) {
			if(humanDTO != null && PatternUtil.validatePattern(humanDTO.getDna(), ApiConstant.ADN_PATTERN_PERSON)) {
				response = service.isMutant(humanDTO) ? 
						new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}else {
				response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				logger.error("Error en el patron ADN");
			}
		}else {
			response = ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
		
		return response;
	}
}
