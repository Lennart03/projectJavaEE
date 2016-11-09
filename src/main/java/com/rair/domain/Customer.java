package com.rair.domain;

import javax.persistence.Entity;

@Entity
public class Customer extends Person{

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Customer(String firstName, String lastName, String email, String password) {
		super(firstName, lastName,email,password);
	}
	
	

}
