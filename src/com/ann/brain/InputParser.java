package com.ann.brain;

import java.io.FileInputStream;
import java.util.ArrayList;

public class InputParser {
	
	private FileInputStream readerImgTrain;
	private FileInputStream readerLabelsTrain;
	private FileInputStream readerImgTest;
	private FileInputStream readerLabelsTest;
	private ArrayList<Data_Tuple> trainData;
	private Data_Tuple[] testData;
	
	public InputParser(String pathImgTrain, String pathLabelsTrain, String pathImgTest, String pathLabelsTest) throws Exception {
		this.readerImgTrain = new FileInputStream(pathImgTrain);
		this.readerLabelsTrain = new FileInputStream(pathLabelsTrain);
		this.readerImgTest = new FileInputStream(pathImgTest);
		this.readerLabelsTest = new FileInputStream(pathLabelsTest);
		this.trainData = new ArrayList<Data_Tuple>();
		this.testData = new Data_Tuple[10000];
	}
	
	public Data_Tuple[] getTestData() {
		return this.testData;
	}
	
	public ArrayList<Data_Tuple> getTrainingData() {
		return this.trainData;
	}
	
	public void parseTrainingData() {
		int[] magicNLabel = new int[4];
		int[] magicNData = new int[4];

		try {
			int[] garbage = new int[16];
			double[] data = new double[784];
			double[] label;
			
			magicNData[0] = this.readerImgTrain.read(); magicNData[1] = this.readerImgTrain.read(); magicNData[2] = this.readerImgTrain.read(); magicNData[3] = this.readerImgTrain.read();
			magicNLabel[0] = this.readerLabelsTrain.read(); magicNLabel[1] = this.readerLabelsTrain.read(); magicNLabel[2] = this.readerLabelsTrain.read(); magicNLabel[3] = this.readerLabelsTrain.read();
			for (int i = 0; i < 12; i++) {
				garbage[i] = this.readerImgTrain.read();
			}
			for (int i = 0; i < 4; i++) {
				garbage[12+i] = this.readerLabelsTrain.read();

			}
			
			for (int i = 0; i < 60000; i++) {
				label = this.format(this.readerLabelsTrain.read());
				for (int j = 0; j < 784; j++) {
					data[j] = this.readerImgTrain.read();
				}
				this.trainData.add(new Data_Tuple(data, label));
			}
						
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void parseTestData() {
		int[] magicNLabel = new int[4];
		int[] magicNData = new int[4];

		try {
			int[] garbage = new int[16];
			double[] data = new double[784];
			double[] label;
			
			magicNData[0] = this.readerImgTest.read(); magicNData[1] = this.readerImgTest.read(); magicNData[2] = this.readerImgTest.read(); magicNData[3] = this.readerImgTest.read();
			magicNLabel[0] = this.readerLabelsTest.read(); magicNLabel[1] = this.readerLabelsTest.read(); magicNLabel[2] = this.readerLabelsTest.read(); magicNLabel[3] = this.readerLabelsTest.read();
			for (int i = 0; i < 12; i++) {
				garbage[i] = this.readerImgTest.read();
			}
			for (int i = 0; i < 4; i++) {
				garbage[12+i] = this.readerLabelsTest.read();

			}
			
			for (int i = 0; i < 10000; i++) {
				label = this.format(this.readerLabelsTest.read());
				for (int j = 0; j < 784; j++) {
					data[j] = this.readerImgTest.read();
				}
				this.testData[i] = new Data_Tuple(data, label);
			}
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public double[] format(int num) {
		switch (num) {
		case 0:
			double[] sol = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			return sol;
		case 1:
			double[] sol1 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
			return sol1;
		case 2:
			double[] sol2 = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
			return sol2;
		case 3:
			double[] sol3 = {0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
			return sol3;
		case 4:
			double[] sol4 = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
			return sol4;
		case 5:
			double[] sol5 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
			return sol5;
		case 6:
			double[] sol6 = {0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
			return sol6;
		case 7:
			double[] sol7 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
			return sol7;
		case 8:
			double[] sol8 = {0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
			return sol8;
		case 9:
			double[] sol9 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
			return sol9;
		default:
			double[] so = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			return so;
		}
	}

}
