package com.neuralnetwork.math;

/**
 * Helper class with math functions to be performed on two-dimensional input arrays of neural network.
 */
public class NNMath {

	/**
	 * Consolidates two equal sized double arrays by summing up values in same positions.
	 *
	 * @param a first array
	 * @param b second array
	 * @return new array with same length as input arrays, and values equal to summed values in same positions of input arrays.
	 */
	public static double[] sumArrays(double[] a, double[] b) {
		double[] result = new double[a.length];
		for(int i = 0; i < a.length; i++) {
			result[i] = a[i] + b[i];
		}
		return result;
	}

	/**
	 * Consolidates two equal sized double arrays by multiplying values in same positions.
	 *
	 * @param a first array
	 * @param b second array
	 * @return new array with same length as input arrays, and values equal to product of values in same positions of input arrays.
	 */
	public static double[] multiplyArrays(double[] a, double[] b) {
		double[] result = new double[a.length];
		for(int i = 0; i < a.length; i++) {
			result[i] = a[i] * b[i];
		}
		return result;
	}
}
