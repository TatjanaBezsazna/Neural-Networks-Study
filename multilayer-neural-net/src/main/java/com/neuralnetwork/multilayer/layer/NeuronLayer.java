package com.neuralnetwork.multilayer.layer;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.neuralnetwork.multilayer.activation.ActivationFunction;
import com.neuralnetwork.multilayer.neuron.Neuron;

/**
 * Representation of a single layer of a multilayer perceptron.
 */
public interface NeuronLayer {

	ArrayList<Neuron> getNeurons();

	void setNeurons(final ArrayList<Neuron> neurons);

	void addNeuron(final Neuron neuron);

	ActivationFunction getActivationFunction();

	void setActivationFunction(final ActivationFunction activationFunction);

	NeuronLayer getPreviousLayer();

	void setPreviousLayer(final NeuronLayer neuronLayer);

	NeuronLayer getNextLayer();

	void setNextLayer(final NeuronLayer neuronLayer);

	BigDecimal[] calculateOutput();

	BigDecimal[] feedForwardInput(BigDecimal[] input);
}
