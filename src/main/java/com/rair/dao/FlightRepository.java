package com.rair.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Airport;
import com.rair.domain.Flight;

@Stateless
public class FlightRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	private static final String QUERY_START = "SELECT f FROM Flight f ";
	private static final Long AIRLINE_ID = 1L;
	
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
	
	public List<Flight> retrieveFlightsByDestination(Airport airport){
		List<Flight> flights = entityManager.createQuery(QUERY_START + "where f.arrivalDestination.name = " + airport.getName(),Flight.class).getResultList();
		return flights;
	}
	
	public List<Flight> retrieveFutureFlightsByAirline(){
		Date date2 = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		List<Flight> flights =  entityManager.createQuery(QUERY_START + "where f.airline.id = " + AIRLINE_ID,Flight.class).getResultList();
		List<Flight> futureFlights = new ArrayList<Flight>();
		for(Flight f:flights){
			
        	Date date1 = f.getDepartureTime();
        	
        	System.out.println(date1.toString());
        	System.out.println(date2.toString());

        	Calendar cal1 = Calendar.getInstance();
        	cal1.setTime(date1);
        	
        	if(cal1.after(cal2)){
        		futureFlights.add(f);
        	}
		}
		return futureFlights;
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
	
	public Flight update(Flight flight, Long flightId){
    	Flight oldFlight = entityManager.find(Flight.class, flightId);
    	oldFlight = flight;
    	entityManager.merge(oldFlight);
    	return oldFlight;
    }
}
