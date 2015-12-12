package com.ann.brain;

public class ANN_Utils {
	
	public ANN_Utils() {}
	
	public double sigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}
	
	public double sigmoidPrime(double x) {
		return this.sigmoid(x) * (1 - this.sigmoid(x));
	}
	
	public double[] sigmoidArray(double[] x) {
		double[] sol = new double[x.length];
		for (int i = 0; i < sol.length; i++) {
			sol[i] = this.sigmoid(x[i]);
		}
		return sol;
	}
	
	public double[] sigmoidPrimeArray(double[] x) {
		double[] sol = new double[x.length];
		for (int i = 0; i < sol.length; i++) {
			sol[i] = this.sigmoidPrime(x[i]);
		}
		return sol;
	}
	
	public double[] sumArray(double[] v1, double[] v2) throws Exception {
		if (v1.length != v2.length) {
			throw new Exception("Wrong Input, Incopatible length");
		}
		double[] sol = new double[v1.length];
		for (int i = 0; i < sol.length; i++) {
			sol[i] = v1[i] + v2[i];
		}
		return sol;
	}
	
	public double[] substractArray(double[] v1, double[] v2) throws Exception {
		if (v1.length != v2.length) {
			throw new Exception("Wrong Input, Incopatible length");
		}
		double[] sol = new double[v1.length];
		for (int i = 0; i < sol.length; i++) {
			sol[i] = v1[i] - v2[i];
		}
		return sol;
	}
	
	public double[] itemwiseMultip(double[] v1, double[] v2) throws Exception {
		if (v1.length != v2.length) {
			throw new Exception("Wrong Input, Incopatible length");
		}
		double[] sol = new double[v1.length];
		for (int i = 0; i < sol.length; i++) {
			sol[i] = v1[i] * v2[i];
		}
		return sol;
	}
	
	public int indexOfMax(double[] arr) {
		double max = arr[0];
		int ind = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
				ind = i;
			}
		}
		return ind;
	}
	
}
