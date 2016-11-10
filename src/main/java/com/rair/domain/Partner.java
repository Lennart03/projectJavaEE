package com.rair.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Partner extends Person{
	
	@ManyToOne
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partner other = (Partner) obj;
		if (airline == null) {
			if (other.airline != null)
				return false;
		} else if (!airline.equals(other.airline))
			return false;
		return true;
	}

	
	
	

}
