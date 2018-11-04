/*
package com.neuralnetwork.multilayer.activation.impl;

import com.neuralnetwork.multilayer.activation.ActivationFunction;
import org.springframework.stereotype.Component;

*/
/**
 * Sinusoid activation function. Calculation is based on:
 * <p>
 * y = sin(x)
 *//*

@Component
public class SinusoidActivationFunction implements ActivationFunction {

	*/
/**
	 * {@inheritDoc}
	 *//*

	@Override
	public double calculateOutput(double summedInput) {
		return Math.sin(summedInput);
	}

	*/
/**
	 * {@inheritDoc}
	 *//*

	@Override
	public double calculateDerivative(double summedInput) {
		return Math.cos(summedInput);
	}
}
*/
