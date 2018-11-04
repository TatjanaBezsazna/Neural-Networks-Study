package com.neuralnetwork.multilayer;

import com.neuralnetwork.multilayer.activation.impl.SigmoidActivationFunction;
import com.neuralnetwork.multilayer.network.NeuralNetwork;
import com.neuralnetwork.multilayer.network.impl.MultilayerNeuralNetwork;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Spring context configuration class for neural-network module.
 */
@Component
@ComponentScan
public class AppContext {

	public static void main(String[] args) {
		try {
			NeuralNetwork neuralNetwork = new MultilayerNeuralNetwork(2, new SigmoidActivationFunction());
			neuralNetwork.train(1000, 3);
			neuralNetwork.think(0);
			neuralNetwork.think(1);
			neuralNetwork.think(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
