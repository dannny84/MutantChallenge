/**
 * 
 */
package com.mercadolibre.challenge.util;

/**
 * @author dannny84@gmail.com
 *
 */
public class ApiConstant {

	

	private ApiConstant() {
		
	}
	
	public static final String WRONG_HUMAN = "I need a human !!! ";
	public static final String WRONG_HUMAN_DNA =  "I need a human dna!!! ";
	
	public static final String ADN_PATTERN_MUTANT = "([ACGT])\\1{3,}";
	public static final String ADN_PATTERN_PERSON = "^[ACGT]+$";
	
}
