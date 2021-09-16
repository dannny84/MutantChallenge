package com.mercadolibre.challenge.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;

/**
 * controller that returns the version of the application
 * 
 * @author dannny84@gmail.com
 */
@RestController
@NoArgsConstructor
@ApiResponses(value = {
  @ApiResponse(code = 200, message = "OK", response = String.class),
  @ApiResponse(code = 401, message = "Unauthorized"),
  @ApiResponse(code = 500, message = "Internal Server Error")
})
@Api(value = "Version ApiController", tags = {"", ""})
@RequestMapping("/api")
public class ApiController {

  @Value("${server.servlet.context-path}")
  private String version;

  /**
   * Method that indicate version of application
   * @return ResponseEntity<Object>
   */
  @ApiOperation(value = "Retorna la version de la aplicacion", 
		  		notes = "indica la version de la aplicacion")
  @GetMapping
  public @ResponseBody ResponseEntity<String> version() {
	String versionResponse = "Version " + version;
    return ResponseEntity.ok(versionResponse);
  }
  
}
