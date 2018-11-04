/*
package com.neuralnetwork.multilayer.activation.impl;


import com.neuralnetwork.multilayer.activation.ActivationFunction;
import org.springframework.stereotype.Component;

*/
/**
 * Rectified Linear activation function
 *//*

@Component
public class RectifiedLinearActivationFunction implements ActivationFunction {
	*/
/**
	 * {@inheritDoc}
	 *//*

	@Override
	public double calculateOutput(double summedInput) {
		return Math.max(0, summedInput);
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
