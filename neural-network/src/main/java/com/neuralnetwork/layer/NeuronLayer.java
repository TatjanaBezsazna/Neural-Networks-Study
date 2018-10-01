package com.neuralnetwork.layer;

import com.neuralnetwork.activation.ActivationFunction;
import com.neuralnetwork.math.NNMath;

/**
 * The neuron layer that holds weights for the layer in form of a array.
 */
public class NeuronLayer {

	private ActivationFunction activationFunction;

	private double[] weights;

	public NeuronLayer(int numberOfInputsPerNeuron, ActivationFunction activationFunction) {
		weights = new double[numberOfInputsPerNeuron];

		for (int i = 0; i < numberOfInputsPerNeuron; i++) {
			weights[i] = (2 * Math.random()) - 1; // shift the range from 0-1 to -1 to 1
		}

		this.activationFunction = activationFunction;
	}

	public void adjustWeights(double[] adjustment) {
		this.weights = NNMath.sumArrays(weights, adjustment);
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public double[] getWeights() {
		return weights;
	}
}

