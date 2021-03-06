/**
 * 
 */
package com.mercadolibre.challenge.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.database.model.HumanEntity;
import com.mercadolibre.challenge.database.repository.IHumanRepository;
import com.mercadolibre.challenge.domain.HumanDTO;
import com.mercadolibre.challenge.service.IHumanService;
import com.mercadolibre.challenge.util.ApiConstant;
import com.mercadolibre.challenge.util.PatternUtil;

/**
 * Servicio responsable de la logica de negocio de Humanos
 * 
 * @author dannny84@gmail.com
 *
 */
@Service
public class HumanService implements IHumanService {
	
	private static final int PROJECTIONS = 3;
	private static final int MIN_REPEATED_MUTANT = 2;
	
	/**
	 * Inyeccion del repository
	 */
	@Autowired 
	private IHumanRepository repository;

	/*
	 * (non-Javadoc)
	 * @see com.mercadolibre.challenge.service.IMutantService#isMutant(com.mercadolibre.challenge.domain.HumanDTO)
	 */
	@Override
	public boolean isMutant(HumanDTO human) {
		
		validateHuman(human);
		
		Optional<HumanEntity> humanEntity = repository.findByDna(human.getDna().toString());
		
		boolean isMutant;
		int countFound = 0;
		int projectionInit = 1;
		
		if(humanEntity.isPresent()) {
			isMutant = humanEntity.get().isMutant();
		}else {			
			Character[][] matrix = getMatrix(human.getDna());
			
			isMutant = validateADNMutant(matrix, projectionInit, countFound);
			
			repository.save(new HumanEntity(human.getDna().toString(), isMutant));
		}

		return isMutant;
	}

	/*
	 * (non-Javadoc)
	 * @see com.mercadolibre.challenge.service.IHumanService#countByIsMutant(boolean)
	 */
	@Override
	public long countByIsMutant(boolean isMutant) {
		return this.repository.countByIsMutant(isMutant);
	}

	/*
	 * (non-Javadoc)
	 * @see com.mercadolibre.challenge.service.IHumanService#count()
	 */
	@Override
	public long count() {
		return this.repository.count();
	}
	
	/**
	 * Valida la informacion contenidad en HumantDTO
	 * @param human
	 */
	private void validateHuman(HumanDTO human) {
		if(human == null) {
			throw new IllegalArgumentException(ApiConstant.WRONG_HUMAN);
		}
		
		if(human.getDna() == null) {
			throw new IllegalArgumentException(ApiConstant.WRONG_HUMAN_DNA);
		}
	}
	
	/**
	 * Obtiene la matriz desde la lista de ADN ingresada
	 * 
	 * @param input
	 * @return Matriz
	 */
	private Character[][] getMatrix(List<String> input) {
		int col = input.get(0).length();
		int row = input.size();
		Character[][] matrix = new Character[row][col];
		if (col != row) {
			throw new IllegalArgumentException(ApiConstant.WRONG_SEQUENCE_DNA);
		}

		for (int i = 0; i < row; i++) {
			if ( input.get(i).length() != col) {
				throw new IllegalArgumentException(ApiConstant.WRONG_SEQUENCE_DNA);
			}
			char[] letter =  input.get(i).toCharArray();
			for (int j = 0; j < col; j++) {
				matrix[i][j] = letter[j];
			}
		}
		return matrix;
	}

	/**
	 * valida el ADN de la persona para determinar si es un Mutante o no
	 * 
	 * @param matrix
	 * @param projectionNumber
	 * @param countADNRepeated
	 * @return true si es Mutante y false en caso contrario
	 */
	private boolean validateADNMutant(Character[][] matrix, int projectionNumber, long countADNRepeated) {
		if (countADNRepeated >= MIN_REPEATED_MUTANT) {
			return true;
		} else if (projectionNumber > PROJECTIONS) {
			return false;
		} else {
			List<String> myData = getData(matrix, projectionNumber);

			countADNRepeated += PatternUtil.countPattern(myData, ApiConstant.ADN_PATTERN_MUTANT);
			return validateADNMutant(matrix, ++projectionNumber, countADNRepeated);
		}
	}
	
	/**
	 * Obtiene la data en diferentes direcciones
	 * 
	 * @param matrix
	 * @param order
	 * @return Lista de ADN
	 */
	private List<String> getData(Character[][] matrix, final int order) {
		List<String> data;
		
		switch(order) {
		case 1:
		case 2:
			data = IntStream.range(0, matrix.length).mapToObj(i -> 
						IntStream.range(0, matrix[0].length)
								 .mapToObj(j -> order == 1 ? matrix[i][j] : matrix[j][i])
								 .map(String::valueOf)
								 .collect(Collectors.joining())
					).collect(Collectors.toList());
			break;
		case 3:
			data = getDataDiagonal(matrix);
			break;
		default:
			data = new ArrayList<>();
		}
		
		return data;
	}
	
	/**
	 * Obtiene los elementos de la diagonal de una matriz
	 * pasada por parametro 
	 *  
	 * @param matrix
	 * @return
	 */
	private List<String> getDataDiagonal(Character [][] matrix) {
	    int row = 0;
	    int column;
	    int lastColumn;
	    int lastRow;
	    int seq = 0;
	    List<String> data = new ArrayList<>();
	    
		column = matrix.length - 1;
	    lastColumn = column;
	    lastRow = row;
	    
	    StringBuilder sb = new StringBuilder();
	    
	    do {
	    	sb.append(matrix[row][column]);

			if ((seq == 0 && column == matrix.length - 1) || (seq == 1 && row == matrix.length - 1)) {
				data.add(sb.toString());
				sb = new StringBuilder();
			}

			if (column == matrix.length - 1 && lastColumn > 0) {
				row = 0;
				column = lastColumn - 1;
				lastColumn = column;
			} else if (row == matrix.length - 1 || column == matrix.length - 1) {
				row = lastRow + 1;
				column = 0;
				lastRow = row;
				seq = 1;
			} else {
				row++;
				column++;
			}
	    } while ((row != matrix.length));
	    
	    return data;
	}

}
