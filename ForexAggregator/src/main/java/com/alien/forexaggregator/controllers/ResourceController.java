package com.alien.forexaggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.alien.forexaggregator.models.Currency;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ResourceController {

	@Autowired
	Currency currency;
	@Autowired
	ObjectMapper objectMapper;
	
	@GetMapping("/CurrencyNameList")
	public ResponseEntity<String> getListOfCurrenyISOcode(){
		  String json=null;
		  try {
	            json = objectMapper.writeValueAsString(currency.getCurrencyCCode());
	           
	        } catch (JsonProcessingException e) {
	        	return new ResponseEntity<String>("Hey we caught error",HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		return new ResponseEntity<>(json,HttpStatus.OK);
	}
}
