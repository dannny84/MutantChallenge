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

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * controller that returns the version of the application
 * 
 * @author dannny84@gmail.com
 */
@RestController
@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 500, message = "Internal Server Error") })
@Api(value = "Version ApiController", tags = { "", "" })
@RequestMapping("/api")
public class ApiController {

	@Value("${server.servlet.context-path}")
	private String version;

	/**
	 * Controla la cantidad de peticiones al servicio
	 */
	private final Bucket bucket;

	/**
	 * Constructor
	 * 
	 * @param tokensAllowed
	 * @param tokensDuration
	 */
	@Autowired
	public ApiController(@Value("${security.tokens.max}") int tokensAllowed, @Value("${security.tokens.duration}") int tokensDuration) {
		Bandwidth limit = Bandwidth.classic(tokensAllowed, Refill.greedy(tokensAllowed, Duration.ofSeconds(tokensDuration)));
		this.bucket = Bucket4j.builder()
				.addLimit(limit)
				.build();
	}

	/**
	 * Method that indicate version of application
	 * 
	 * @return ResponseEntity<Object>
	 */
	@ApiOperation(value = "Retorna la version de la aplicacion", notes = "indica la version de la aplicacion")
	@GetMapping
	public @ResponseBody ResponseEntity<String> version() {
		if (bucket.tryConsume(1)) {
			return ResponseEntity.ok("Version " + version);
		}else {
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
	}

}
