package com.rair.dao;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Flight;

public class FlightRepositiory {
	
	@PersistenceContext
	EntityManager entityManager;
	
	private static final String QUERY_START = "SELECT f FROM Flight f ";

	
	public void createFlight(Flight flight) {
		entityManager.persist(flight);
	}
	
	public void deleteFlight(Flight flight) {
		entityManager.remove(flight);
	}
	
	public Flight retrieveFlightByFlightNumber(String flightNumber) {
		Flight flight = entityManager.createQuery(QUERY_START+"WHERE f.flightNumber = '"+flightNumber+"'", Flight.class).getSingleResult();
		return flight;
	}
	
	public List<Flight> retrieveAllFlights() {
		List<Flight> flights = entityManager.createQuery(QUERY_START, Flight.class).getResultList();
		return flights;
	}
	
	public List<Flight> retrieveFlightsOnDate(Date date) {
		List<Flight> flights = entityManager.createQuery(QUERY_START+ "WHERE departureTime ='" +date+"'", Flight.class).getResultList();
		return flights;
	}
	
	public List<Flight> retrieveFlightsSortedOnPrice(){
		List<Flight> flights = retrieveAllFlights();
		flights.sort(new Comparator<Flight>() {

			@Override
			public int compare(Flight flight1, Flight flight2) {
				return flight1.getBasePrice().compareTo(flight2.getBasePrice());
			}
		});
		return flights;
	}
	
	public List<Flight> retrieveFlightsSortedOnDepartureTime(){
		List<Flight> flights = retrieveAllFlights();
		flights.sort(new Comparator<Flight>() {

			@Override
			public int compare(Flight flight1, Flight flight2) {
				return flight1.getDepartureTime().compareTo(flight2.getDepartureTime());
			}
		});
		return flights;
	}
	
	public List<Flight> retieveFlightsSortedOnAirport() {
		List<Flight> flights = retrieveAllFlights();
		flights.sort(new Comparator<Flight>() {

			@Override
			public int compare(Flight flight1, Flight flight2) {
				return flight1.getArrivalDestination().compareTo(flight2.getArrivalDestination());
			}
		});
		return flights;
	}
}
