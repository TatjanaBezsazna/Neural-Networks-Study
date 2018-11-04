package com.neuralnetwork.multilayer.network.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import com.neuralnetwork.math.NNMath;
import com.neuralnetwork.multilayer.activation.ActivationFunction;
import com.neuralnetwork.multilayer.constants.NeuralNetConstants;
import com.neuralnetwork.multilayer.layer.NeuronLayer;
import com.neuralnetwork.multilayer.layer.impl.NeuronLayerImpl;
import com.neuralnetwork.multilayer.mnist.MNISTReader;
import com.neuralnetwork.multilayer.network.NeuralNetwork;
import com.neuralnetwork.multilayer.neuron.Neuron;

/**
 * Multilayer neural network.
 */
public class MultilayerNeuralNetwork implements NeuralNetwork {

	private BigDecimal[][] inputs;

	private BigDecimal[][] expectedOutputs;

	private NeuronLayer firstHiddenLayer; //head

	private NeuronLayer outputLayer; //tail

	private int hiddenLayersCount;

	private BigDecimal learningRate = BigDecimal.valueOf(0.5);

	private BigDecimal momentum = BigDecimal.valueOf(0.8);

	private BigDecimal allowedError = BigDecimal.valueOf(0.01);

	public MultilayerNeuralNetwork(final int numberOfHiddenLayers, final ActivationFunction activationFunction) throws IOException, MNISTReader
			.InvalidFileFormatException {
		this.inputs = transformToBlackAndWhite(MNISTReader.readImages(NeuralNetConstants.TRAINING_IMAGES_FILE_NAME));
		this.expectedOutputs = transformToBigDeicmals(MNISTReader.readLabels(NeuralNetConstants.TRAINING_LABELS_FILE_NAME));
		this.hiddenLayersCount = numberOfHiddenLayers;
		buildChainOfLayers(activationFunction);
	}

	private BigDecimal[][] transformToBlackAndWhite(final double[][] initialData) {
		final BigDecimal[][] transformedData = new BigDecimal[initialData.length][initialData[0].length];
		for (int i = 0; i < initialData.length; i++) {
			for(int j = 0; j < initialData[0].length; j++) {
				double element = initialData[i][j];
				double transformedElement = 0;
				if(element >= 255 / 2) {
					transformedElement = 1;
				}
				transformedData[i][j] = BigDecimal.valueOf(transformedElement);
			}
		}
		return transformedData;
	}

	private BigDecimal[][] transformToBigDeicmals(final double[][] initialData) {
		final BigDecimal[][] transformedData = new BigDecimal[initialData.length][initialData[0].length];
		for (int i = 0; i < initialData.length; i++) {
			for(int j = 0; j < initialData[0].length; j++) {
				double element = initialData[i][j];
				transformedData[i][j] = BigDecimal.valueOf(element);
			}
		}
		return transformedData;
	}

	private void buildChainOfLayers(final ActivationFunction activationFunction) {
		final int inputSize = inputs[0].length;
		final int outputSize = expectedOutputs[0].length;
		final int hiddenNeuronsCount = (int) Math.sqrt(inputSize * outputSize);
		//Raw inputs from MNIST library already represent an input layer, so the network construction starts at first hidden layer
		this.firstHiddenLayer = new NeuronLayerImpl(hiddenNeuronsCount, inputSize, activationFunction);
		this.outputLayer = this.firstHiddenLayer;
		for(int i = 0; i < this.hiddenLayersCount - 1; i++) {
			NeuronLayer newLayer = new NeuronLayerImpl(hiddenNeuronsCount, hiddenNeuronsCount, activationFunction);
			newLayer.setPreviousLayer(this.outputLayer);
			this.outputLayer.setNextLayer(newLayer);
			this.outputLayer = newLayer;
		}
		NeuronLayer actualOutputLayer = new NeuronLayerImpl(outputSize, hiddenNeuronsCount, activationFunction);
		actualOutputLayer.setPreviousLayer(this.outputLayer);
		this.outputLayer.setNextLayer(actualOutputLayer);
		this.outputLayer = actualOutputLayer;
	}

	@Override
	public void train(int iterationCount, int trainingSetSize) {
		if(trainingSetSize > this.inputs.length) {
			throw new IllegalArgumentException("Training set size has to be less than 60000");
		}
		final long start = System.currentTimeMillis();
		int iterationsLeft = iterationCount;
		BigDecimal consolidatedError = null;
		while(iterationsLeft-- > 0) {
			consolidatedError = BigDecimal.ZERO;
			for (int i = 0; i < trainingSetSize; i++) {
				final BigDecimal[] outputs = this.firstHiddenLayer.feedForwardInput(inputs[i]);
				final BigDecimal squaredError = calculateSquaredError(this.expectedOutputs[i], outputs);
				if(squaredError.compareTo(allowedError) == 1) {
					backPropagateError(this.expectedOutputs[i], outputs);
				}
				consolidatedError = consolidatedError.add(squaredError);
			}
			System.out.println("Iterations left: " + iterationsLeft + ". Consolidated error: " + consolidatedError);
			if(consolidatedError.divide(BigDecimal.valueOf(trainingSetSize), 5, BigDecimal.ROUND_DOWN).compareTo(allowedError) < 1) {
				break;
			}
		}
		final long end = System.currentTimeMillis() - start;
		System.out.println("Finished training with " + iterationCount + " iterations, " + trainingSetSize + " records, "
				+ this.hiddenLayersCount + " hidden layers in "
				+ TimeUnit.MILLISECONDS.toMinutes(end) + " minutes."
		);
		System.out.println("Learning rate: " + learningRate + " momentum: " + momentum);
		System.out.println("Average error of last iteration is: " + consolidatedError.divide(BigDecimal.valueOf(trainingSetSize), 5, BigDecimal.ROUND_DOWN).toString());
	}

	private BigDecimal calculateSquaredError(final BigDecimal[] expectedOutputs, final BigDecimal[] actualOutputs) {
		BigDecimal squaredError = BigDecimal.ZERO;
		for(int i = 0; i < expectedOutputs.length; i++) {
			double currentOutputError = Math.pow(expectedOutputs[i].subtract(actualOutputs[i]).doubleValue(), 2) / 2;
			squaredError = squaredError.add(BigDecimal.valueOf(currentOutputError));
		}
		return squaredError;
	}


	private void backPropagateError(final BigDecimal[] expectedOutputs, final BigDecimal[] actualOutputs) {
		BigDecimal[] weightedOutputDeltasSums = adjustWeightsForOutputLayer(expectedOutputs, actualOutputs);
		recursivelyAdjustWeightsForHiddenLayers(this.outputLayer.getPreviousLayer(), weightedOutputDeltasSums);
	}

	private BigDecimal[] adjustWeightsForOutputLayer(final BigDecimal[] expectedOutputs, final BigDecimal[] actualOutputs) {
		final int neuronsInLastHiddenLayerCount = this.outputLayer.getPreviousLayer().getNeurons().size();
		BigDecimal[] weightedDeltasForLayer = NNMath.createArrayOfZeros(neuronsInLastHiddenLayerCount);
		for (int i = 0; i < expectedOutputs.length; i++) {
			Neuron neuron = this.outputLayer.getNeurons().get(i);
			BigDecimal error = expectedOutputs[i].subtract(actualOutputs[i]);
			BigDecimal delta = this.outputLayer.getActivationFunction().calculateDerivative(neuron.getSummedWeightedInput()).multiply(error);
			weightedDeltasForLayer = NNMath.sumArrays(weightedDeltasForLayer, calculateWeightedDeltas(neuron, delta));

			BigDecimal[] weightAdjustment = calculateWeightAdjustment(neuron, delta);
			neuron.adjustWeights(weightAdjustment, this.momentum);
		}
		return weightedDeltasForLayer;
	}

	private void recursivelyAdjustWeightsForHiddenLayers(final NeuronLayer layer, final BigDecimal[] previousLayerWeightedDeltas) {
		if (layer == null) {
			return;
		}
		final int neuronsInCurrentLayerCount = layer.getNeurons().size();
		// In order to avoid nullpointer in case of no previous layer, neurons in previous layer are always 1 less then inputs,
		// because last input is a threshold input
		final int neuronsInPreviousLayerCount = layer.getNeurons().get(0).getInputs().length - 1;
		BigDecimal[] weightedDeltasForLayer = NNMath.createArrayOfZeros(neuronsInPreviousLayerCount);
		for (int i = 0; i < neuronsInCurrentLayerCount; i++) {
			Neuron neuron = layer.getNeurons().get(i);
			BigDecimal delta = layer.getActivationFunction().calculateDerivative(neuron.getSummedWeightedInput()).multiply(previousLayerWeightedDeltas[i]);
			weightedDeltasForLayer = NNMath.sumArrays(weightedDeltasForLayer, calculateWeightedDeltas(neuron, delta));

			BigDecimal[] weightAdjustment = calculateWeightAdjustment(neuron, delta);
			neuron.adjustWeights(weightAdjustment, this.momentum);
		}

		recursivelyAdjustWeightsForHiddenLayers(layer.getPreviousLayer(), weightedDeltasForLayer);
	}

	private BigDecimal[] calculateWeightedDeltas(final Neuron neuron, final BigDecimal delta) {
		//not considering the last weight, because it corresponds to threshold
		BigDecimal[] weightedDeltas = new BigDecimal[neuron.getWeights().length - 1];
		for(int i = 0; i < weightedDeltas.length; i++) {
			weightedDeltas[i] = neuron.getWeights()[i].multiply(delta);
		}
		return weightedDeltas;
	}

	private BigDecimal[] calculateWeightAdjustment(final Neuron neuron, final BigDecimal delta) {
		BigDecimal[] weightAdjustment = new BigDecimal[neuron.getInputs().length];
		for(int i = 0; i < weightAdjustment.length; i++) {
			weightAdjustment[i] = neuron.getInputs()[i].multiply(delta).multiply(this.learningRate);
		}
		return  weightAdjustment;
	}

	@Override
	public void think(final int indexInTestingSet) throws IOException, MNISTReader.InvalidFileFormatException {
		//this.inputs = transformToBlackAndWhite(MNISTReader.readImages(NeuralNetConstants.TESTING_IMAGES_FILE_NAME));
		//this.expectedOutputs = transformToBigDeicmals(MNISTReader.readLabels(NeuralNetConstants.TESTING_LABELS_FILE_NAME));
		if(indexInTestingSet > this.inputs.length) {
			throw new IllegalArgumentException("Training set size has to be less than 60000");
		}
		final BigDecimal[] outputs = this.firstHiddenLayer.feedForwardInput(inputs[indexInTestingSet]);
		final BigDecimal squaredError = calculateSquaredError(this.expectedOutputs[indexInTestingSet], outputs);
		System.out.println("Classification result: ");
		System.out.println(buildResultComparisonTable(expectedOutputs[indexInTestingSet],outputs));
		System.out.println("Squared Error: " + squaredError);
	}

	private String buildResultComparisonTable(final BigDecimal[] expectedOutputs, final BigDecimal[] actual) {
		StringBuilder builder = new StringBuilder("NEURON | EXPECTED | ACTUAL\n");
		for(int i = 0; i < expectedOutputs.length; i++) {
			builder.append(i + ".     |")
					.append(expectedOutputs[i].toString() + "      | ")
					.append(actual[i].toString())
					.append("\n");
		}
		return builder.toString();
	}

}
