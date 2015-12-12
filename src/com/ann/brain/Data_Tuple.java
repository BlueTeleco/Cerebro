package com.ann.brain;

public class Data_Tuple {
	
	private double[] imgData;
	private double[] y;
	
	public Data_Tuple(double[] img, double[] y) {
		this.imgData = img.clone();
		this.y = y;
	}
	
	public double[] getData() {
		return this.imgData;
	}
	public double[] getSolution() {
		return this.y;
	}

}
