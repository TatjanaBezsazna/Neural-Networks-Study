package com.neuralnetwork.multilayer.activation.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;

import com.neuralnetwork.multilayer.activation.ActivationFunction;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.spi.ApfloatBuilder;
import org.apfloat.spi.ApfloatImpl;
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
	private BigDecimal slope = BigDecimal.ONE;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calculateOutput(BigDecimal summedInput) {
		BigDecimal denominator = BigDecimal.valueOf(1d).add(BigDecimal.valueOf(Math.exp(-slope.multiply(summedInput).doubleValue())));
		return BigDecimal.ONE.divide(denominator,30, RoundingMode.DOWN);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal calculateDerivative(BigDecimal summedInput) {
		BigDecimal output = calculateOutput(summedInput);
		return output.multiply(BigDecimal.ONE.subtract(output));
	}

	public void setSlope(BigDecimal slope) {
		this.slope = slope;
	}
}
