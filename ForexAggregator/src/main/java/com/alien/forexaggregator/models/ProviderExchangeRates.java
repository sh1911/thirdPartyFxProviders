package com.alien.forexaggregator.models;

public class ProviderExchangeRates {

	private String nameofProvider;
	private String base;
	private String to;
	private Float perRate;
	private Float resultValue;

	public String getNameofProvider() {
		return nameofProvider;
	}

	public void setNameofProvider(String nameofProvider) {
		this.nameofProvider = nameofProvider;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Float getPerRate() {
		return perRate;
	}

	public void setPerRate(Float perRate) {
		this.perRate = perRate;
	}

	public void setResultValue(Float f) {
		this.resultValue = f;

	}

	public Float getResultValue() {
		return resultValue;
	}

	@Override
	public String toString() {
		return "ProviderExchangeRates [nameofProvider=" + nameofProvider + ", base=" + base + ", to=" + to
				+ ", perRate=" + perRate + ", resultValue=" + resultValue + "]";
	}

}
