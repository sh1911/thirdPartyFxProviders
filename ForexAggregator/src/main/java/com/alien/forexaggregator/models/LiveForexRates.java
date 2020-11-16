package com.alien.forexaggregator.models;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveForexRates {
	@JsonProperty
	private String base;
	@JsonProperty
	private String date;
	@Autowired
	@JsonProperty("rates")
	private Map<String, Object> rates;

	public LiveForexRates() {

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

	public Map<String, Object> getRates() {
		return rates;
	}

	public void setRates(Map<String, Object> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "RateModel [base=" + base + ", date=" + date + ", rates=" + rates + "]";
	}

}
