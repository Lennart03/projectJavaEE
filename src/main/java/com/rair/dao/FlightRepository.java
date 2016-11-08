package com.rair.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	public List<Flight> retrieveFutureFlightsByAirline() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date date2 = sdf.parse(new Date().toString());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		List<Flight> flights =  entityManager.createQuery(QUERY_START + "where f.airline.id = " + AIRLINE_ID,Flight.class).getResultList();
		for(Flight f:flights){
			
        	Date date1 = sdf.parse(f.getDepartureTime().toString());
        	
        	System.out.println(sdf.format(date1));
        	System.out.println(sdf.format(date2));

        	Calendar cal1 = Calendar.getInstance();
        	cal1.setTime(date1);
        	
        	if(cal1.before(cal2)){
        		flights.remove(f);
        	}
		}
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
