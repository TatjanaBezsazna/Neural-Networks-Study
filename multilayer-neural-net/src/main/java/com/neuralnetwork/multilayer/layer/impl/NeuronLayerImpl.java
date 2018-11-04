package com.neuralnetwork.multilayer.layer.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.IntStream;

import com.neuralnetwork.multilayer.activation.ActivationFunction;
import com.neuralnetwork.multilayer.layer.NeuronLayer;
import com.neuralnetwork.multilayer.neuron.Neuron;
import com.neuralnetwork.multilayer.neuron.impl.NeuronImpl;

/**
 * Input layer of a multi-layer neural network. Has not activation functions or weights. Simply contains input data.
 */
public class NeuronLayerImpl implements NeuronLayer {

	private ArrayList<Neuron> neurons;

	private ActivationFunction activationFunction;

	private NeuronLayer previousLayer;

	private NeuronLayer nextLayer;

	public NeuronLayerImpl(final int numberOfNeurons, final int inputsPerNeuron, final ActivationFunction activationFunction) {
		this.neurons = new ArrayList<>(numberOfNeurons);
		this.activationFunction = activationFunction;
		IntStream.range(0, numberOfNeurons).forEach(index -> neurons.add(index, new NeuronImpl(inputsPerNeuron)));
	}

	@Override
	public BigDecimal[] calculateOutput() {
		BigDecimal[] output = new BigDecimal[neurons.size()];
		for(int i = 0; i < neurons.size(); i++) {
			BigDecimal summedWeightedInput = neurons.get(i).getSummedWeightedInput();
			output[i] = activationFunction.calculateOutput(summedWeightedInput);
		}
		return output;
	}

	@Override
	public BigDecimal[] feedForwardInput(BigDecimal[] inputs) {
		this.neurons.forEach(neuron -> neuron.setInputs(inputs));
		if(this.nextLayer == null) {
			return calculateOutput();
		}
		return this.nextLayer.feedForwardInput(calculateOutput());
	}

	@Override
	public ArrayList<Neuron> getNeurons() {
		return this.neurons;
	}

	@Override
	public void setNeurons(final ArrayList<Neuron> neurons) {
		this.neurons = neurons;
	}

	@Override
	public void addNeuron(final Neuron neuron) {
		this.neurons.add(neuron);
	}

	@Override
	public ActivationFunction getActivationFunction() {
		return this.activationFunction;
	}

	@Override
	public void setActivationFunction(final ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	@Override
	public NeuronLayer getPreviousLayer() {
		return this.previousLayer;
	}

	@Override
	public void setPreviousLayer(final NeuronLayer neuronLayer) {
		this.previousLayer = neuronLayer;
	}

	@Override
	public NeuronLayer getNextLayer() {
		return this.nextLayer;
	}

	@Override
	public void setNextLayer(final NeuronLayer neuronLayer) {
		this.nextLayer = neuronLayer;
	}
}
