package com.neuralnetwork.math;

import java.math.BigDecimal;

/**
 * Helper class with math functions to be performed on two-dimensional input arrays of neural network.
 */
public class NNMath {

	/**
	 * Consolidates two equal sized BigDecimal arrays by summing up values in same positions.
	 *
	 * @param a first array
	 * @param b second array
	 * @return new array with same length as input arrays, and values equal to summed values in same positions of input arrays.
	 */
	public static BigDecimal[] sumArrays(BigDecimal[] a, BigDecimal[] b) {
		BigDecimal[] result = new BigDecimal[a.length];
		for(int i = 0; i < a.length; i++) {
			result[i] = a[i].add(b[i]);
		}
		return result;
	}

	/**
	 * Consolidates two equal sized BigDecimal arrays by multiplying values in same positions.
	 *
	 * @param a first array
	 * @param b second array
	 * @return new array with same length as input arrays, and values equal to product of values in same positions of input arrays.
	 */
	public static BigDecimal[] multiplyArrays(BigDecimal[] a, BigDecimal[] b) {
		BigDecimal[] result = new BigDecimal[a.length];
		for(int i = 0; i < a.length; i++) {
			result[i] = a[i].multiply(b[i]);
		}
		return result;
	}

	/**
	 * Multiplies each element in the array by the specified number.
	 *
	 * @param array first array
	 * @param multiplier BigDecimal by which each element in the array should be multiplied.
	 * @return new array with same length as input arrays, and values equal to product of values in the array by multiplier.
	 */
	public static BigDecimal[] multiplyArrayByNumber(BigDecimal[] array, BigDecimal multiplier) {
		BigDecimal[] result = new BigDecimal[array.length];
		for(int i = 0; i < array.length; i++) {
			result[i] = array[i].multiply(multiplier);
		}
		return result;
	}

	public static BigDecimal[] createArrayOfZeros(int size) {
		BigDecimal[] array = new BigDecimal[size];
		for(int i = 0; i < size; i++) {
			array[i] = BigDecimal.ZERO;
		}
		return array;
	}
}
