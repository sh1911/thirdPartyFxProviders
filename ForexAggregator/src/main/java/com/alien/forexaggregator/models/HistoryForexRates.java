package com.alien.forexaggregator.models;

import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoryForexRates {

	@JsonProperty("rates")
	private TreeMap<String, Map<String, Object>> rates;

	public HistoryForexRates() {
	}

	public TreeMap<String, Map<String, Object>> getRates() {
		return rates;
	}

	public void setRates(TreeMap<String, Map<String, Object>> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "HistoryForexRates [rates=" + rates + "]";
	}

}
