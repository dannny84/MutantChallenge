/**
 * 
 */
package com.mercadolibre.challenge.util;

/**
 * Clase de Constantes 
 * 
 * @author dannny84@gmail.com
 *
 */
public class ApiConstant {

	private ApiConstant() {
	}
	
	public static final String WRONG_HUMAN = "Necesito un DNA Humano !!! ";
	public static final String WRONG_HUMAN_DNA =  "Necesito un DNA válido!!! ";
	public static final String WRONG_SEQUENCE_DNA =  "La secuencia No tiene las medidas validas";
	
	public static final String ADN_PATTERN_MUTANT = "([ACGT])\\1{3,}";
	public static final String ADN_PATTERN_PERSON = "^[ACGT]+$";
	
}
