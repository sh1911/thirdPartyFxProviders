package com.alien.bloodbrother.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;
@Component
@Table(name = "Donar_Table")
@Entity
public class Donar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "Name", nullable = false)
	private String name;
	@Column(name = "Email", nullable = false)
	private String email;
	@Column(name = "BloodGroup", nullable = false)
	private String bloodgroup;
	@Column(name = "City", nullable = false)
	private String city;
	@Column(name = "Zipcode", nullable = false)
	private String zipcode;
	@Column(name = "State", nullable = false)
	private String state;
	@Column(name = "Country", nullable = false)
	private String country;
	@Column(nullable = false,updatable = true)
	@CreationTimestamp
	private Date date;
	  
	      
	@Override
	public String toString() {
		return "Donar [id=" + id + ", name=" + name + ", email=" + email + ", bloodgroup=" + bloodgroup + ", sign="
				+ "" + ", city=" + city + ", zipcode=" + zipcode + ", state=" + state + ", country=" + country
				+ ", date=" + date + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBloodgroup() {
		return bloodgroup;
	}
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
	
	
}
