package com.neuralnetwork.activation.impl;

import com.neuralnetwork.activation.ActivationFunction;
import org.springframework.stereotype.Component;

/**
 * Sigmoid activation function. Calculation is based on:
 *
 * y = 1/(1+ e^(-slope*x))
 *
 */
@Component
public class SigmoidActivationFunction implements ActivationFunction {
	/**
	 * Slope parameter
	 */
	private double slope = 1d;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculateOutput(double summedInput) {
		double denominator = 1d + Math.exp(-slope * summedInput);
		return (1d / denominator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double calculateDerivative(double summedInput) {
		double output = calculateOutput(summedInput);
		return output * (1d - output);
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}
}
