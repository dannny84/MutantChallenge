package com.mercadolibre.challenge;

import java.time.Duration;
import java.time.Instant;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.mercadolibre.challenge.config.AppConfig;

/**
 * Main Class - Mutant Api
 * @author dannny84@gmail.com
 * 
 */
@SpringBootApplication
@Import (value= {AppConfig.class})
public class MutantApiApplication {

	private static Logger logger = Logger.getLogger(MutantApiApplication.class);
	
	/**
	 * Metodo principal para iniciar el Spring Boot Application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.info("Initializing MutantApiApplication");
		
		Instant start = Instant.now();
		
		SpringApplication.run(MutantApiApplication.class, args);
		
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();

		logger.info(String.format("Started MutantApiApplication - API in %s ms", timeElapsed));
	}

}
