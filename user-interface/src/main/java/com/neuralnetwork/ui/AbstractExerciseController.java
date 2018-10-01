package com.neuralnetwork.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.neuralnetwork.dto.LearningDataSetDto;
import com.neuralnetwork.neuralnet.NeuralNet;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * Abstract controller for exercises.
 */
@Controller
public class AbstractExerciseController {

	private final LearningDataSetDto learningDataSetDto;

	private String linkToAction;

	@Autowired
	private NeuralNet neuralNet;

	AbstractExerciseController() {
		this.learningDataSetDto = new LearningDataSetDto();
	}

	public String onGet(final Model model) {
		setUpDefaultModelAttributes(model);
		return "exercise";
	}

	public String onPost(final Model model, final double learningRate, final int numberOfIterations, final String activationFunction) {
		neuralNet.setUp(learningDataSetDto, activationFunction);
		final int iterationsPerformed = neuralNet.train(numberOfIterations, learningRate);
		final double[] outputs = neuralNet.bulkThink(learningDataSetDto.getInputs());
		final String[] roundedOutputs = new String[outputs.length];
		for (int i = 0; i < outputs.length; i++) {
			roundedOutputs[i] = new BigDecimal(outputs[i]).setScale(5, RoundingMode.HALF_UP).toString();
		}
		setUpDefaultModelAttributes(model);
		model.addAttribute("iterationsPerformed", iterationsPerformed);
		model.addAttribute("finalWeights", neuralNet.getLayer().getWeights());
		model.addAttribute("activationFunction", activationFunction);
		model.addAttribute("outputs", roundedOutputs);
		return "exercise";
	}

	private void setUpDefaultModelAttributes(final Model model) {
		model.addAttribute("trainingInputLayers", learningDataSetDto.getInputs());
		model.addAttribute("trainingOutputLayers", learningDataSetDto.getOutputs());
		model.addAttribute("linkToAction", linkToAction);
		model.addAttribute("math", new MathTool());
	}

	void configure(final String linkToAction) {
		this.linkToAction = linkToAction;
	}

	LearningDataSetDto getLearningDataSetDto() {
		return learningDataSetDto;
	}
}
