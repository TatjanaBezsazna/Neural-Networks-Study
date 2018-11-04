package com.neuralnetwork.multilayer.network;

import java.io.IOException;

import com.neuralnetwork.multilayer.mnist.MNISTReader;

/**
 * Represents a multilayer neural network.
 */
public interface NeuralNetwork {

	void train(int iterationCount, int trainingSetSize);

	void think(int indexInTestingSet) throws IOException, MNISTReader.InvalidFileFormatException;

}
