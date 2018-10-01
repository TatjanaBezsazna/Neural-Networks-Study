package com.neuralnetwork.ui;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for exercise one.
 */
@Controller
public class ExerciseOneController extends AbstractExerciseController {

	private static final String linkToAction = "/exercise-one";

	ExerciseOneController() {
		getLearningDataSetDto().addTrainingSet(new double[]{1, -0.2, 0.5}, 0);
		getLearningDataSetDto().addTrainingSet(new double[]{1, 0.2, -0.5}, 0);
		getLearningDataSetDto().addTrainingSet(new double[]{1, 0.8, -0.8}, 1);
		getLearningDataSetDto().addTrainingSet(new double[]{1, 0.8, 0.8}, 1);
	}

	@RequestMapping(value = linkToAction, method = RequestMethod.GET)
	public String onGet(final Model model) {
		return super.onGet(model);
	}

	@RequestMapping(value = linkToAction, method = RequestMethod.POST)
	public String onPost(final Model model,
						 @RequestParam("learningRate") final double learningRate,
						 @RequestParam("numberOfIterations") final int numberOfIterations,
						 @RequestParam("activationFunction") final String activationFunction) {
		return super.onPost(model, learningRate, numberOfIterations, activationFunction);
	}

	@PostConstruct
	private void configure() {
		super.configure(linkToAction);
	}
}
