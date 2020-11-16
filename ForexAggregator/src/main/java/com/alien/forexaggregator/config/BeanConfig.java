package com.alien.forexaggregator.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.alien.forexaggregator.models.Currency;
import com.alien.forexaggregator.models.ForexProviders;
import com.alien.forexaggregator.models.MaxMinAvg;
import com.alien.forexaggregator.models.ProviderExchangeRates;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfig {
	
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	@Bean
	public Currency getListOfCurrency() {
		return new Currency();
	}
	@Bean
	public ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	@Bean
	@Qualifier("today")
	public Date getDate() {
		return new Date();
	}
	@Bean
	@Qualifier("format")
	public DateFormat getDateFormat() {
		
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	@Bean
	@Qualifier("calender")
	public Calendar getCalender() {
		return new GregorianCalendar();
	}
	@Bean
	public MaxMinAvg getMaxMinAugObj() {
		return new MaxMinAvg();
	}
	@Bean
	public ForexProviders getForexProvider() {
		return new ForexProviders();
	}
	@Bean
	public ProviderExchangeRates getExchangeRatesOfProvider() {
		return new ProviderExchangeRates();
	}
	
	 
	

}
