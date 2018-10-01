package com.neuralnetwork.neuralnet;

import java.util.ArrayList;

import com.neuralnetwork.dto.LearningDataSetDto;
import com.neuralnetwork.layer.NeuronLayer;

/**
 * The neural network interface.
 */
public interface NeuralNet {

	/**
	 * Calculates the outputs of a given input set.
	 *
	 * @param inputs the input set of the neural network
	 * @return the calculated output for the input array
	 */
	double think(double[] inputs);

	/**
	 * Performs think operation on multiple input arrays.
	 *
	 * @param inputLayers an array list of input layers
	 * @return the array of outputs where index of output matches the index of input in inputLayers param.
	 */
	double[] bulkThink(ArrayList<double[]> inputLayers);

	/**
	 * Runs the training loop for the neural network.
	 * The training involves calculating the output and then adjusting the weights accordingly.
	 *
	 * @param numberOfTrainingIterations number of iterations (epochs) for the training
	 * @param trainingRate               the desired training rate
	 * @return number of training iterations performed
	 */
	int train(int numberOfTrainingIterations, double trainingRate);

	/**
	 * Sets up the neural network before train or think operation.
	 *
	 * @param learningDataSetDto the learning dataset dto object
	 * @param activationFunction activation function name for the neuron.
	 */
	void setUp(final LearningDataSetDto learningDataSetDto, final String activationFunction);

	/**
	 * Gets the active layer of the neural network.
	 *
	 * @return neuron layer that is being trained.
	 */
	NeuronLayer getLayer();
}
