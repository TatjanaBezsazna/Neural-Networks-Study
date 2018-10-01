package com.neuralnetwork.activation.impl;

import com.neuralnetwork.activation.ActivationFunction;
import org.springframework.stereotype.Component;

/**
 * Linear combination activation function implementation, the output unit is
 * simply the weighted sum of its inputs.
 */
@Component
public class LinearActivationFunction implements ActivationFunction {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculateOutput(double summedInput) {
		return summedInput;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculateDerivative(double summedInput) {
		return 1;
	}
}
