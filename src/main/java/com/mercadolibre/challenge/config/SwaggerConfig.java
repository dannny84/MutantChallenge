/**
 * 
 */
package com.mercadolibre.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dannny84@gmail.com
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * Patron Controller.
	 */
	public static final String DEFAULT_INCLUDE_PATTERN = "/controller/.*";
	
	@Value("${swagger.base-package}")
	private String basePackage;
	
	@Value("${swagger.title}")
	private String title;
	
	@Value("${swagger.version}")
	private String version;


	/**
	 * Configure API Docket
	 * 
	 * @return Docket instance.
	 */
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiEndPointsInfo()).useDefaultResponseMessages(false);
	}

	/**
	 * Configure API Information.
	 * 
	 * @return ApiInfo instance.
	 */
	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder()
				.title(title)
				.version(version).build();
	}
}
