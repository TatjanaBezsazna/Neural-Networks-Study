package com.neuralnetwork.activation;

/**
 * Neural network's activation function interface.
 */
public interface ActivationFunction {

	/**
	 * Performs calculation based on the sum of input neurons output.
	 *
	 * @param summedInput
	 *            neuron's sum of outputs respectively inputs for the connected
	 *            neuron
	 *
	 * @return Output's calculation based on the sum of inputs
	 */
	double calculateOutput(double summedInput);

	/**
	 * Performs derivative of the function represented by a class calculation based sum of input neurons output.
	 *
	 * @param summedInput
	 *            neuron's sum of outputs respectively inputs for the connected
	 *            neuron
	 *
	 * @return derivative calculated based on neuron output
	 */
	double calculateDerivative(double summedInput);
}
