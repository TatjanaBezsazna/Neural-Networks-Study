package com.neuralnetwork.multilayer.mnist;

import java.io.DataInputStream;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

/**
 * Reader for MNIST files.
 * Borrowed from https://github.com/surmenok/MNISTNeuralNetwork/blob/master/src/main/java/com/surmenok/pavel/mnist/MNISTReader.java
 */
public class MNISTReader {

	private static final int IMAGES_MAGIC_NUMBER = 2051;
	private static final int LABELS_MAGIC_NUMBER = 2049;

	public static double[][] readImages(String fileName) throws IOException, InvalidFileFormatException {
		try (DataInputStream stream = new DataInputStream(new ClassPathResource(fileName).getInputStream())) {
			int magicNumber = stream.readInt();
			if (magicNumber != IMAGES_MAGIC_NUMBER) {
				throw new InvalidFileFormatException("Wrong magic number: " + magicNumber + "; expected: 2051");
			}

			int imageCount = stream.readInt();
			int rowCount = stream.readInt();
			int columnCount = stream.readInt();

			double[][] allImages = new double[imageCount][rowCount * columnCount];

			for (int i = 0; i < imageCount; i++) {
				if (stream.available() <= 0) {
					throw new InvalidFileFormatException("The file contains less than " + imageCount + " images");
				}

				for (int column = 0; column < columnCount; column++) {
					for (int row = 0; row < rowCount; row++) {
						allImages[i][column * rowCount + row] = stream.readUnsignedByte();
					}
				}
			}

			return allImages;
		}
	}

	public static double[][] readLabels(String fileName) throws IOException, InvalidFileFormatException {
		try (DataInputStream stream = new DataInputStream(new ClassPathResource(fileName).getInputStream())) {

			int magicNumber = stream.readInt();
			if (magicNumber != LABELS_MAGIC_NUMBER) {
				throw new InvalidFileFormatException("Wrong magic number: " + magicNumber + "; expected: 2049");
			}

			int labelCount = stream.readInt();

			double[][] allLabels = new double[labelCount][10];

			for (int i = 0; i < labelCount; i++) {
				if (stream.available() <= 0) {
					throw new InvalidFileFormatException("The file contains less than " + labelCount + " images");
				}

				byte label = stream.readByte();
				allLabels[i][label % 10] = 1;
			}

			return allLabels;
		}
	}

	public static class InvalidFileFormatException extends Exception {
		InvalidFileFormatException(String message) {
			super(message);
		}
	}
}
