package com.neuralnetwork.activation.impl;

import com.neuralnetwork.activation.ActivationFunction;
import org.springframework.stereotype.Component;

/**
 * Tanh activation function. Calculation is based on:
 *
 * y = tanh(x)
 *
 */
@Component
public class TanhActivationFunction implements ActivationFunction {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculateOutput(double summedInput) {
		return Math.sinh(summedInput) / Math.cosh(summedInput);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculateDerivative(double summedInput) {
		return 1 - Math.pow(calculateOutput(summedInput), 2);
	}
}
