package com.alien.forexaggregator.controllers;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alien.forexaggregator.models.ForexProviders;
import com.alien.forexaggregator.models.HistoryForexRates;
import com.alien.forexaggregator.models.MaxMinAvg;
import com.alien.forexaggregator.models.ProviderExchangeRates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


	



@RestController
public class ForexAggregatorController {
	@Autowired RestTemplate restTemplate;
	@Autowired
	@Qualifier("today")
	Date endDate;
	@Autowired
	@Qualifier("format")
	DateFormat df;
	@Autowired
	@Qualifier("calender")
	Calendar cal;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MaxMinAvg maxMinAvg;
	@Autowired
	ForexProviders forexProviders;
	@Value("${rest.latest-url}")
	private String latestURL;
	@Value("${rest.history-url}")
	private String historyURL;
	private String json=null;
	@GetMapping("/convert/")
	public ResponseEntity<String> getListOfexchangeCurrencyProviders(@RequestParam("base") String base,@RequestParam("to") String to,@RequestParam("amount") Float amount) throws JsonMappingException, JsonProcessingException
	{
		List<ProviderExchangeRates> list=new ArrayList<>();
		ForexProviders provider;
		
		String noOfProviders=restTemplate.getForObject(latestURL+"noOfProviders",String.class);
		for(int i=1;i<=Integer.parseInt(noOfProviders);i++) {
			ProviderExchangeRates providerExchangeRates=new ProviderExchangeRates();
			try {
				provider=restTemplate.getForObject(latestURL+i+"?base="+base, ForexProviders.class);
			}
			catch (Exception e) {
				return new ResponseEntity<String>("Hey we caught error",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			providerExchangeRates.setBase(provider.getBase());
			providerExchangeRates.setTo(to);
			providerExchangeRates.setNameofProvider(provider.getNameOfProvider());
			providerExchangeRates.setPerRate(new BigDecimal((Double)provider.getRates().get(to)).setScale(5, RoundingMode.HALF_EVEN).floatValue());
			providerExchangeRates.setResultValue(new BigDecimal( amount/(Double)provider.getRates().get(to)).setScale(4, RoundingMode.HALF_EVEN).floatValue());
			list.add(providerExchangeRates);
		}
		try {
			json=objectMapper.writeValueAsString(list);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Hey we caught error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(json,HttpStatus.OK);
	}
	@GetMapping("/history/{unitTime}")
	public ResponseEntity<String> getHistoryList(@PathVariable("unitTime") int gap,@RequestParam("base") String base,@RequestParam("to") String to) {
		
		
		cal.setTime(endDate);
		cal.add(Calendar.DAY_OF_MONTH, -gap);
		Date lastDate = cal.getTime();
		String URL=historyURL+"?start_at="+df.format(lastDate)+"&end_at="+df.format(endDate)+"&base="+base+"&symbols="+to;
		
		  try {
			  	HistoryForexRates result=restTemplate.getForObject(URL,HistoryForexRates.class);
	            json = objectMapper.writeValueAsString(result);
	           
	        } catch (JsonProcessingException e) {
	        	return new ResponseEntity<String>("Hey we caught error",HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		 
		return new ResponseEntity<String>(json,HttpStatus.OK);
	}
	@GetMapping("/history/days/{unitTime}")
	public ResponseEntity<String> getMinMaxAvg(@PathVariable("unitTime") int gap,@RequestParam("base") String base,@RequestParam("to") String to){
	
	
		Double max=Double.MIN_VALUE,min=Double.MAX_VALUE,avg=0d,count=0d;
		HistoryForexRates result;
		cal.setTime(endDate);
		cal.add(Calendar.DAY_OF_MONTH, -gap);
		Date lastDate = cal.getTime();
		String URL=historyURL+"?start_at="+df.format(lastDate)+"&end_at="+df.format(endDate)+"&base="+base+"&symbols="+to;
		try {
			 result=restTemplate.getForObject(URL,HistoryForexRates.class);
		}
		catch (Exception e) {
			return new ResponseEntity<String>("Hey we caught error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		for(String key:result.getRates().keySet()) {
			for(String innerKey:result.getRates().get(key).keySet()) {
				
				max=Math.max(max,(double) result.getRates().get(key).get(innerKey));
				min=Math.min(min,(double)result.getRates().get(key).get(innerKey));
				avg=avg+ (double) result.getRates().get(key).get(innerKey);
				count++;
				
			}
		}
		maxMinAvg.setMax(new BigDecimal(max).setScale(5, RoundingMode.HALF_EVEN).doubleValue());
		maxMinAvg.setMin(new BigDecimal(min).setScale(5, RoundingMode.HALF_EVEN).doubleValue());
		maxMinAvg.setAverage(new BigDecimal(avg/count).setScale(5, RoundingMode.HALF_EVEN).doubleValue());
		System.out.println();
		try {
	            json = objectMapper.writeValueAsString(maxMinAvg);
	           
	        } catch (JsonProcessingException e) {
	        	return new ResponseEntity<String>("Hey we caught error",HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		
		return new ResponseEntity<String>(json,HttpStatus.OK);
	}
}

