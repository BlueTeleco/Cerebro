package com.ann.brain;

import java.util.ArrayList;

public interface Trainer {
	
	public void train(ArrayList<Data_Tuple> data, Data_Tuple[] testData, int epochs) throws Exception;
	
	public void test(Data_Tuple[] testData) throws Exception;
	
	public int guessNumber(double[] inputs) throws Exception;
	
	public double[] guess(double[] input) throws Exception;

}
