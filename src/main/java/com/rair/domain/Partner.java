package com.rair.domain;

public class Partner extends Person{
	
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
