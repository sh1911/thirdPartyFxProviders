package com.alien.forexaggregator.models;

public class MaxMinAvg {

	private Double max;
	private Double min;
	private Double average;

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	@Override
	public String toString() {
		return "MaxMinAvg [max=" + max + ", min=" + min + ", average=" + average + "]";
	}

}
