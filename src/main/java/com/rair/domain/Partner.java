package com.rair.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Partner extends Person{
	
	@ManyToOne
	@JoinColumn(name = "AirlineID", nullable = false)
	private Airline airline;
	
	public Partner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Partner(Airline airline) {
		super();
		this.airline = airline;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	
	
	

}
