package com.rair.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Airport implements Comparable<Airport>, Serializable {
	
	private static final long serialVersionUID = 4933804867200626434L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotBlank
	@Size(max = 100)
	private String name;

	@Column
	@NotBlank
	@Size(max = 100)
	private String country;

	@Column
	@NotBlank
	@Size(max = 100)
	private String city;

	@Enumerated(EnumType.STRING)
	private Region region;

	@Column
	@NotBlank
	@Size(max = 100)
	private String airportCode;

	public Airport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Airport(String name, String country, String city, Region region, String airportCode) {
		super();
		this.name = name;
		this.country = country;
		this.city = city;
		this.region = region;
		this.airportCode = airportCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Override
	public int compareTo(Airport o) {
		return name.compareTo(o.getName());
	}

}
