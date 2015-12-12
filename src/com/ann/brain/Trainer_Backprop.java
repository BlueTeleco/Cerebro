package com.ann.brain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

public class Trainer_Backprop implements Trainer {
	
	private Brain ann;
	private ANN_Utils utl = new ANN_Utils();
	private int nLayers;
	private int[] neurEachLayer;
	private static final double learnC = 0.1;
	
	public Trainer_Backprop(int layers, int[] neurons) {
		this.nLayers = layers;
		this.neurEachLayer = neurons.clone();
		this.ann = new Brain(layers, this.neurEachLayer);
		
	}
	
	public double[] guess(double[] input) throws Exception {
		return this.ann.feedfoward(input);
	}
	
	public int guessNumber(double[] inputs) throws Exception {
		double[] sol = this.ann.feedfoward(inputs);
		return utl.indexOfMax(sol);
	}
	
	public void train(ArrayList<Data_Tuple> data, Data_Tuple[] testData, int epochs) throws Exception {
		int batch_size = 10;
		for (int i = 0; i < epochs; i++) {
			Collections.shuffle(data);
			ArrayList<ArrayList<Data_Tuple>> batches = new ArrayList<ArrayList<Data_Tuple>>();
			for (int j = 0; j < data.size(); j+=batch_size) {
				batches.add(new ArrayList<Data_Tuple>(data.subList(j, j+batch_size)));
			}
			for (ArrayList<Data_Tuple> bat : batches) {
				this.updateANN(bat);
			}
			if (testData != null) {
				System.out.println("Epoch: " + (i+1));
				this.test(testData);
			}
		}
		
	}
	
	public void updateANN(ArrayList<Data_Tuple> bat) throws Exception {
		double[][] var_bia = new double[this.nLayers - 1][];
		Array2DRowRealMatrix[] var_wei = new Array2DRowRealMatrix[this.nLayers - 1];
		for (int i = 0; i < var_wei.length; i++) {
			var_wei[i] = new Array2DRowRealMatrix(this.neurEachLayer[i+1], this.neurEachLayer[i]);
			var_bia[i] = new double[this.neurEachLayer[i+1]];
		}
		for (int i = 0; i < var_wei.length; i++) {
			for (int j = 0; j < this.neurEachLayer[i+1]; j++) {
				var_bia[i][j] = 0;
				for (int k = 0; k < this.neurEachLayer[i]; k++) {
					var_wei[i].setEntry(j, k, 0);
				}
			}
		}
		
		for (Data_Tuple tuple : bat) {
			this.backprop(var_wei, var_bia, tuple);
		}
		
		for (int i = 0; i < var_wei.length; i++) {
			for (int j = 0; j < var_wei[i].getRowDimension(); j++) {
				this.ann.setBias(var_bia[i][j], i, j);
				for (int k = 0; k < var_wei[i].getColumnDimension(); k++) {
					this.ann.setWeigth(this.ann.getWeight(i, j, k) - learnC*var_wei[i].getEntry(j, k)/bat.size(), i, j, k);
				}
			}
		}
		
	}
	
	public void backprop(Array2DRowRealMatrix[] var_wei, double[][] var_bia, Data_Tuple data) throws Exception {
		double[][] activations = new double[this.nLayers][];
		double[][] zs = new double[this.nLayers-1][];
		Stack<List<Double>> deltas = new Stack<List<Double>>();
		double[] a = data.getData();
		double[] z;
		
		activations[0] = a;
		for (int i = 0; i < var_wei.length; i++) {
			z = utl.sumArray(this.ann.getAllWeights()[i].operate(a), this.ann.getAllBiases()[i]);
			a = utl.sigmoidArray(z);
			activations[i+1] = a;
			zs[i] = z;
		}

		deltas.push(Arrays.asList(ArrayUtils.toObject(
				utl.itemwiseMultip(
						utl.substractArray(activations[activations.length-1], data.getSolution())
						, utl.sigmoidPrimeArray(zs[zs.length-1])
				)
		)));
		for (int i = 2; i <= zs.length; i++) {
			deltas.push(Arrays.asList(ArrayUtils.toObject(
					utl.itemwiseMultip(
							this.ann.getAllWeights()[this.ann.getAllWeights().length-i+1].transpose()
									.operate(ArrayUtils.toPrimitive(deltas.peek().toArray(new Double[deltas.peek().size()])))
							, utl.sigmoidPrimeArray(zs[zs.length-i])
					)
			)));
		}
		
		for (int i = 0; i < var_wei.length; i++) {
			List<Double> next_delta = deltas.pop();
			Array2DRowRealMatrix a_temp = new Array2DRowRealMatrix(activations[i]);
			double[] temp1 = ArrayUtils.toPrimitive(next_delta.toArray(new Double[next_delta.size()]));
			Array2DRowRealMatrix d_temp = new Array2DRowRealMatrix(temp1);
			var_bia[i] = utl.sumArray(var_bia[i], ArrayUtils.toPrimitive(next_delta.toArray(new Double[next_delta.size()])));
			var_wei[i] = var_wei[i].add((Array2DRowRealMatrix) d_temp.multiply(a_temp.transpose()));
		}
		
	}
		
	public void test(Data_Tuple[] testData) throws Exception {
		int bullseye = 0;
		for (int i = 0; i < testData.length; i++) {
			if (utl.indexOfMax(testData[i].getSolution()) == this.guessNumber(testData[i].getData())) {
				bullseye++;
			}
		}
		System.out.println("Aciertos: " + bullseye);
		System.out.println("Total: " + testData.length);
		System.out.println("Porcentaje: " + bullseye * 100.0 / testData.length + "%");
		System.out.println("");
	}	

}
