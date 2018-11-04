package com.neuralnetwork.multilayer.neuron;

import java.math.BigDecimal;

import com.neuralnetwork.multilayer.layer.NeuronLayer;

/**
 * Represents a single neuron in a {@link NeuronLayer} of a multilayer perceptron.
 */
public interface Neuron {

	BigDecimal[] getWeights();

	void setWeights(final BigDecimal[] weights);

	BigDecimal[] getInputs();

	void setInputs(BigDecimal[] inputs);

	BigDecimal getSummedWeightedInput();

	void adjustWeights(BigDecimal[] weightAdjustment, BigDecimal momentum);
}
