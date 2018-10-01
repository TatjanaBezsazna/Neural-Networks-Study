package com.neuralnetwork.dto;

import java.util.ArrayList;

/**
 * DTO holding input layer data and corresponding expected outputs as arrays
 * multiple inputs are expected to generate single output.
 */
public class LearningDataSetDto {

	private ArrayList<double[]> inputs;

	private ArrayList<Double> outputs;

	public ArrayList<double[]> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList<double[]> inputs) {
		this.inputs = inputs;
	}

	public ArrayList<Double> getOutputs() {
		return outputs;
	}

	public void setOutputs(ArrayList<Double> outputs) {
		this.outputs = outputs;
	}

	public void addTrainingSet(double[] inputLayer, double output) {
		if(inputs == null) {
			inputs = new ArrayList<>();
			outputs = new ArrayList<>();
		}
		inputs.add(inputLayer);
		outputs.add(output);
	}
}
