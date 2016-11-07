package com.rair.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Airline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;

	@OneToMany
	@JoinTable(name = "jnd_airline_flight", joinColumns = @JoinColumn(name = "airline_fk"), inverseJoinColumns = @JoinColumn(name = "flight_fk"))
	private List<Flight> flights = new ArrayList<>();

	public Airline() {
		super();
	}

	public Airline(String name) {

		super();
		this.name = name;
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

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	@Override
	public String toString() {
		return "Airline [id=" + id + ", name=" + name + "]";
	}
	
	
	
	

}
