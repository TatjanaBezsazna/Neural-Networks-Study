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
public class ExerciseTwoController extends AbstractExerciseController {

	private static final String linkToAction = "/exercise-two";

	ExerciseTwoController() {
		getLearningDataSetDto().addTrainingSet(new double[]{1, 1,1}, 1);
		getLearningDataSetDto().addTrainingSet(new double[]{1, 1, 0}, -1);
		getLearningDataSetDto().addTrainingSet(new double[]{1, 0, 1}, -1);
		getLearningDataSetDto().addTrainingSet(new double[]{1, 0, 0}, -1);
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
