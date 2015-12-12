package com.ann.brain;

import java.util.Random;

import org.apache.commons.math3.linear.*;

public class Brain {
	
	private int nLayers;
	private int[] neurInLayers;
	private double[][] biases;
	private Array2DRowRealMatrix[] weights;
	private static ANN_Utils utl = new ANN_Utils();
	private static Random r = new Random();
	
	public Brain(int layers, int[] neurons) {
		this.nLayers = layers;
		this.neurInLayers = neurons;
		this.biases = new double[layers - 1][];
		this.weights = new Array2DRowRealMatrix[layers - 1];
		for (int i = 0; i < this.weights.length; i++) {
			this.weights[i] = new Array2DRowRealMatrix(neurons[i+1], neurons[i]);
			this.biases[i] = new double[neurons[i+1]];
		}
		for (int i = 0; i < this.weights.length; i++) {
			for (int j = 0; j < neurons[i+1]; j++) {
				biases[i][j] = r.nextGaussian();
				for (int k = 0; k < neurons[i]; k++) {
					this.weights[i].setEntry(j, k, r.nextGaussian());
				}
			}
		}
		
	}

	public int getLayers() {
		return this.nLayers;
	}
	
	public int[] getNeurons() {
		return this.neurInLayers;
	}
	
	public void setBias(double newB, int i, int j) {
		this.biases[i][j] = newB;
	}
	
	public void setWeigth(double newW, int i, int j, int k) {
		this.weights[i].setEntry(j, k, newW);
	}
	
	public void setWeigthsLayer(Array2DRowRealMatrix newW, int i) {
		this.weights[i] = newW;
	}
	
	public void setAllWeights(Array2DRowRealMatrix[] newWs) throws Exception {
		if (newWs.length != (this.nLayers -1)) {
			throw new Exception("Wrong input");
		}
		for (int i = 0; i < this.nLayers - 1; i++){
			if (newWs[i].getRowDimension() != this.neurInLayers[i+1] || newWs[i].getColumnDimension() != this.neurInLayers[i]) {
				throw new Exception("Wrong input");
			}
		}
		this.weights = newWs;
	}
	
	public Array2DRowRealMatrix[] getAllWeights() {
		return this.weights;
	}
	
	public double[][] getAllBiases() {
		return this.biases;
	}
	
	public double getWeight(int x, int y, int z) {
		return this.weights[x].getEntry(y, z);
	}
	
	public int getLessNeurons() {
		int sol = this.neurInLayers[0];
		for (int i = 0; i < this.neurInLayers.length; i++) {
			sol = Math.min(sol, this.neurInLayers[i]);
		}
		return sol;
	}
	
	public double[] feedfoward(double[] input) throws Exception {
		if (input.length != this.neurInLayers[0]) {
			throw new Exception("Wrong Input");
		}
		double[] ioputs = input.clone();
		for (int i = 0; i < this.weights.length; i++) {
			ioputs = utl.sigmoidArray(utl.sumArray(this.weights[i].operate(ioputs), this.biases[i]));
		}
		return ioputs;
	}

}
