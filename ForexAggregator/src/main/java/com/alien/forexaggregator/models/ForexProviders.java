package com.alien.forexaggregator.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForexProviders {
	@JsonProperty("nameOfProvider")
	private String nameOfProvider;
	@JsonProperty("rates")
	private Map<String, Object> rates;
	@JsonProperty("base")
	private String base;
	@JsonProperty("date")
	private String date;

	public String getNameOfProvider() {
		return nameOfProvider;
	}

	public void setNameOfProvider(String nameOfProvider) {
		this.nameOfProvider = nameOfProvider;
	}

	public Map<String, Object> getRates() {
		return rates;
	}

	public void setRates(Map<String, Object> rates) {
		this.rates = rates;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
