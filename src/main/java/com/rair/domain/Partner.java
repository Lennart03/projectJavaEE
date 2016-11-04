package com.rair.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Partner extends Person{
	
	@ManyToOne
	@JoinColumn(name = "Airline ID", nullable = false)
	private Airline airline;

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
