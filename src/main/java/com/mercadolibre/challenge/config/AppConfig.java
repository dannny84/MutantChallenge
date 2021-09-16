package com.mercadolibre.challenge.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application Config
 * 
 * @author dannny84@gmail.com
 *
 */

@Configuration
@EnableConfigurationProperties
@ComponentScan(value= {
		"com.mercadolibre.challenge.controller",
		"com.mercadolibre.challenge.service",
		"com.mercadolibre.challenge.database"})
public class AppConfig {

	
}
