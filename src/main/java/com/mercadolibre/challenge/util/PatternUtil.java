/**
 * 
 */
package com.mercadolibre.challenge.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase utilitaria para el manejo de los patrones
 * 
 * @author dannny84@gmail.com
 *
 */
public class PatternUtil {

	private PatternUtil() {
	}
	
	public static boolean validatePattern(List<String> rowData, String pattern) {
		if(rowData != null) {
			Pattern p = Pattern.compile(pattern);
			return p.matcher(String.join("", rowData)).find();
		}
		return false; 
	}
	
	public static long countPattern(List<String> rowData, String pattern) {
		Pattern p = Pattern.compile(pattern);
		long response = 0l;
		
		if(rowData != null) {
			response = rowData.stream().filter(word -> p.matcher(word).find()).count();
		}
		return response;
	}
}
