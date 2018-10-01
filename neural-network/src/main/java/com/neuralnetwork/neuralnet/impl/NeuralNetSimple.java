package com.neuralnetwork.neuralnet.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.DoubleStream;

import com.neuralnetwork.activation.ActivationFunction;
import com.neuralnetwork.dto.LearningDataSetDto;
import com.neuralnetwork.layer.NeuronLayer;
import com.neuralnetwork.math.NNMath;
import com.neuralnetwork.neuralnet.NeuralNet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeuralNetSimple implements NeuralNet {

	private NeuronLayer layer;

	private LearningDataSetDto dataSetDto;

	@Autowired
	private Map<String, ActivationFunction> activationFunctionsByName;

	/**
	 * {@inheritDoc}
	 */
	public double think(double[] inputLayer) {
		final double summedInput = calculateSummedWeightedInput(inputLayer);
		final double output = layer.getActivationFunction().calculateOutput(summedInput);
		//Rounding to 2 decimal places
		return new BigDecimal(output).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public double[] bulkThink(ArrayList<double[]> inputLayers) {
		double[] outputs = new double[inputLayers.size()];
		for (int i = 0; i < inputLayers.size(); i++) {
			outputs[i] = think(inputLayers.get(i));
		}
		return outputs;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUp(final LearningDataSetDto learningDataSetDto, final String activationFunction) {
		final int numberOfInputsPerLayer = learningDataSetDto.getInputs().get(0).length;
		this.layer = new NeuronLayer(numberOfInputsPerLayer, activationFunctionsByName.get(activationFunction));
		this.dataSetDto = learningDataSetDto;
	}

	/**
	 * {@inheritDoc}
	 */
	public int train(int numberOfTrainingIterations, double trainingRate) {
		final ArrayList<double[]> inputs = dataSetDto.getInputs();
		final ArrayList<Double> expectedOutputs = dataSetDto.getOutputs();
		if (inputs.size() != expectedOutputs.size()) {
			throw new IllegalArgumentException("Input data vector count should be equal to outputs count");
		}
		int actuallyPerformedIterations = 0;
		while (actuallyPerformedIterations < numberOfTrainingIterations) {
			ArrayList<Double> errorsForAllInputSets = new ArrayList<>();
			// pass the training set through the network
			for (int j = 0; j < inputs.size(); j++) {
				double[] inputLayer = inputs.get(j);
				double error = calculateError(inputLayer, expectedOutputs.get(j));
				if (error != 0) {
					// adjust the weights
					double[] adjustmentLayer = calculateWeightAdjustments(inputLayer, error, trainingRate);
					this.layer.adjustWeights(adjustmentLayer);
				}
				errorsForAllInputSets.add(error);
			}
			//If all input sets have been classified correctly - break early
			double consolidatedError = errorsForAllInputSets.stream().mapToDouble(a -> a * a).sum();
			if (consolidatedError == 0) {
				break;
			}
			actuallyPerformedIterations++;
		}
		return actuallyPerformedIterations;
	}

	private double calculateSummedWeightedInput(double[] inputLayer) {
		final double[] weightedInputs = NNMath.multiplyArrays(inputLayer, layer.getWeights());
		return DoubleStream.of(weightedInputs).sum();
	}

	private double calculateError(double[] inputLayer, double expectedOutput) {
		double actualOutput = think(inputLayer);
		return expectedOutput - actualOutput;
	}

	private double[] calculateWeightAdjustments(double[] inputLayer, double error, double trainingRate) {
		// adjust weights by error * input * activation function derivative
		// Calculate how much to adjust the weights by
		double delta = error * layer.getActivationFunction().calculateDerivative(calculateSummedWeightedInput(inputLayer));
		return Arrays.stream(inputLayer)
				.map(input -> input * delta)
				.map(input -> input * trainingRate)
				.toArray();
	}

	public NeuronLayer getLayer() {
		return layer;
	}

}