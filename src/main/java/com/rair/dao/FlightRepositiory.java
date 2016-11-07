package com.rair.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Flight;

public class FlightRepositiory {
	
	@PersistenceContext
	private EntityManager entityManager;

	
	public void createFlight(Flight flight) {
		entityManager.persist(flight);
	}
	
	public void deleteFlight(Flight flight) {
		entityManager.remove(flight);
	}
	
	public List<Flight> retrieveFlightsOrderedOnPrice(){
		return null;
	}
	
	public List<Flight> retrieveFlightsOrderedOnDepartureTime(){
		return null;
	}
	
	public List<Flight> retieveFlightsOrderedOnAirport() {
		return null;
	}
}
