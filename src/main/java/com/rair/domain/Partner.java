package com.rair.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Partner extends Person{
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinTable(name="jnd_airline_partner", joinColumns=@JoinColumn(name="partner_fk"), inverseJoinColumns= @JoinColumn(name="airline_fk"))
	private Airline airline;
	
	public Partner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Partner(String firstName, String lastName, String email, String password, Airline airline) {
		super(firstName, lastName,email,password);
		this.airline = airline;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	
	
	

}
