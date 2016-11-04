package com.rair.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Partner{
	
	@Column(name="Airline")
	private Airline airline;

}
