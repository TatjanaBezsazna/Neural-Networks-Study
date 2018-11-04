package com.neuralnetwork.multilayer.neuron.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.IntStream;

import com.neuralnetwork.math.NNMath;
import com.neuralnetwork.multilayer.neuron.Neuron;

/**
 * Single neuron implementation of a multi-layer neural network.
 */
public class NeuronImpl implements Neuron {

	private BigDecimal[] weights;

	private BigDecimal[] inputs;

	private BigDecimal[] previousIterationAdjustment;

	public NeuronImpl(final int inputsCount) {
		//Adding 1 to inputs count in order to have one threshold input
		this.weights = new BigDecimal[inputsCount + 1];
		this.inputs = new BigDecimal[inputsCount + 1];
		this.previousIterationAdjustment = NNMath.createArrayOfZeros(inputsCount + 1);
		IntStream.range(0, weights.length ).forEach(index -> {
			weights[index] = new BigDecimal(0/*Math.random()*/).setScale(2, RoundingMode.HALF_UP);
		});
	}

	@Override
	public BigDecimal[] getWeights() {
		return this.weights;
	}

	@Override
	public void setWeights(final BigDecimal[] weights) {
		if(weights.length > this.weights.length) {
			throw new IllegalArgumentException("Invalid weights count passed to neuron. "
					+ "Expected: " + this.weights.length
					+ " actual: " + weights.length);
		}
		this.weights = weights;
	}

	@Override
	public BigDecimal[] getInputs() {
		return this.inputs;
	}

	@Override
	public void setInputs(final BigDecimal[] inputs) {
		if(inputs.length > this.inputs.length - 1) {
			throw new IllegalArgumentException("Invalid inputs count passed to neuron. "
					+ "Expected: " + this.inputs.length
					+ " actual: " + inputs.length);
		}
		IntStream.range(0, inputs.length).forEach( (index) -> {
			this.inputs[index] = inputs[index];
		});
		//threshold neuron value - always 1
		this.inputs[inputs.length] = BigDecimal.ONE;
	}

	@Override
	public BigDecimal getSummedWeightedInput() {
		BigDecimal summedWeightedInput = BigDecimal.ZERO;
		for (int i = 0; i < inputs.length; i++) {
			summedWeightedInput = summedWeightedInput.add(inputs[i].multiply(weights[i]));
		}
		//Rounding in order to avoid computational errors linked to high precision
		return summedWeightedInput;
	}

	@Override
	public void adjustWeights(BigDecimal[] weightAdjustment, BigDecimal momentum) {
		weights = NNMath.sumArrays(weights, weightAdjustment);
		//enhanced weights
		weights = NNMath.sumArrays(weights, NNMath.multiplyArrayByNumber(previousIterationAdjustment, momentum));
		this.previousIterationAdjustment = weightAdjustment;
	}

}
