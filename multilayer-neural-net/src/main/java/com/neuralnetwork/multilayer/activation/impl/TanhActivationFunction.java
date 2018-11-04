package com.neuralnetwork.multilayer.activation.impl;

import java.math.BigDecimal;

import com.neuralnetwork.multilayer.activation.ActivationFunction;
import org.springframework.stereotype.Component;


@Component
public class TanhActivationFunction implements ActivationFunction {
	
	@Override
	public BigDecimal calculateOutput(BigDecimal summedInput) {
		return BigDecimal.valueOf(Math.sinh(summedInput.doubleValue()) / Math.cosh(summedInput.doubleValue()));
	}


	@Override
	public BigDecimal calculateDerivative(BigDecimal summedInput) {

		return BigDecimal.valueOf(1 - Math.pow(calculateOutput(summedInput).doubleValue(), 2));
	}
}
