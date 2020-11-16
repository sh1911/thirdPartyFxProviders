package com.alien.forexaggregator.models;

import java.util.Map;
import java.util.TreeMap;

public class Currency {

	Map<String, String> currencyCCode = new TreeMap<>();

	public Currency() {
		this.currencyCCode.put("Canadian Dollar", "CAD");
		this.currencyCCode.put("Philippine Peso", "PHP");
		this.currencyCCode.put("Hungarian Forint", "HUF");
		this.currencyCCode.put("Hong Kong Dollar", "HKD");
		this.currencyCCode.put("Danish Krone", "DKK");
		this.currencyCCode.put("Czech Koruna", "CZK");
		this.currencyCCode.put("Pound Sterling", "GBP");
		this.currencyCCode.put("Icelandic Krona", "ISK");
		this.currencyCCode.put("Russian Ruble", "RUB");
		this.currencyCCode.put("Romanian Leu", "RON");
		this.currencyCCode.put("Brazilian Real", "BRL");
		this.currencyCCode.put("Indian Rupee", "INR");
		this.currencyCCode.put("Indonesian Rupiah", "IDR");
		this.currencyCCode.put("Swedish Krona", "SEK");
		this.currencyCCode.put("Kuna", "HRK");
		this.currencyCCode.put("Japan Yen", "JPY");
		this.currencyCCode.put("Thai Baht", "THB");
		this.currencyCCode.put("Swiss Franc", "CHF");
		this.currencyCCode.put("Euro", "EUR");
		this.currencyCCode.put("Malaysian Ringgit", "MYR");
		this.currencyCCode.put("Bulgarian Lev", "BGN");
		this.currencyCCode.put("Turkish Lira", "TRY");
		this.currencyCCode.put("Chinese Yuan", "CNY");
		this.currencyCCode.put("Norwegian Krone", "NOK");
		this.currencyCCode.put("New Zealand Dollar", "NZD");
		this.currencyCCode.put("SOUTH AFRICA -Rand", "ZAR");
		this.currencyCCode.put("United States Dollar", "USD");
		this.currencyCCode.put("Mexican Peso", "MXN");
		this.currencyCCode.put("Singapore Dollar", "SGD");
		this.currencyCCode.put("Australian Dollar", "AUD");
		this.currencyCCode.put("New Israeli Sheqel", "ILS");
		this.currencyCCode.put("South Korean won", "KRW");
		this.currencyCCode.put("Poland Zloty", "PLN");

	}

	public Map<String, String> getCurrencyCCode() {
		return currencyCCode;
	}

	public void setCurrencyCCode(Map<String, String> currencyCCode) {
		this.currencyCCode = currencyCCode;
	}

	@Override
	public String toString() {
		return "Currency [currencyCCode=" + currencyCCode.toString() + "]";
	}

}
