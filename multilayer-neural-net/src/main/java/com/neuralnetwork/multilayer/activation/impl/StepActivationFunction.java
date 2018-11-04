/*
package com.neuralnetwork.multilayer.activation.impl;


import com.neuralnetwork.multilayer.activation.ActivationFunction;
import org.springframework.stereotype.Component;

*/
/**
 * Step neuron activation function, the output y of this activation function is
 * binary, depending on whether the input meets a specified threshold, 0. The
 * "signal" is sent, i.e. the output is set to one, if the activation meets the
 * threshold.
 *
 *//*

@Component
public class StepActivationFunction implements ActivationFunction {
	*/
/**
	 * Output value if the input is above or equal the threshold
	 *//*

	private static final double Y_ABOVE = 1d;

	*/
/**
	 * Output value if the input is bellow the threshold
	 *//*

	private static final double Y_BELLOW = 0d;

	*/
/**
	 * The output of this activation function is binary, depending on whether
	 * the input meets a specified threshold.
	 *//*

	private static final double THRESHOLD = 0d;

	*/
/**
	 * {@inheritDoc}
	 *//*

	@Override
	public double calculateOutput(double summedInput) {
		if (summedInput >= THRESHOLD) {
			return Y_ABOVE;
		}
		else {
			return Y_BELLOW;
		}
	}

	*/
/**
	 * {@inheritDoc}
	 *//*

	@Override
	public double calculateDerivative(double summedInput) {
		return 1;
	}
}
*/
