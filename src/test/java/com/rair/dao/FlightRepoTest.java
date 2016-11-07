package com.rair.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.rair.domain.Airline;
import com.rair.domain.Airport;
import com.rair.domain.Flight;
import com.rair.domain.Region;
import com.rair.domain.TravelingClass;
import com.rair.testcore.JpaPersistenceTest;

public class FlightRepoTest extends JpaPersistenceTest {

	FlightRepositiory flightRepositiory;
	Flight flight;
	Map<TravelingClass, Integer> numberOfSeats = new HashMap<>();
	Map<TravelingClass, Integer> availableSeats = new HashMap<>();

	@Before
	public void init() {
		flightRepositiory = new FlightRepositiory();
		flightRepositiory.entityManager = entityManager();

		numberOfSeats.put(TravelingClass.ECONOMY, 100);
		numberOfSeats.put(TravelingClass.BUSINESS, 100);
		numberOfSeats.put(TravelingClass.FIRST_CLASS, 50);

		availableSeats.put(TravelingClass.ECONOMY, 75);
		availableSeats.put(TravelingClass.BUSINESS, 50);
		availableSeats.put(TravelingClass.FIRST_CLASS, 5);

		flight = new Flight();
		flight.setBasePrice(100.0);
		flight.setFlightNumber("flightNumberTest");
		
		flight.setBookings(new ArrayList<>());
		flight.setNumberOfSeats(numberOfSeats);
		flight.setAvailableSeats(availableSeats);
	}

	@Test
	public void persitFlightTest() {
		flight.setAirline(new Airline());
		flight.setDepartureDestination(
				new Airport("Brussels Airport", "Belgium", Region.EUROPE, "Such airport code, WOW"));
		flight.setArrivalDestination(new Airport("Arrival Airport", "Arrival Country", Region.OCEANIA, "Such arrival"));
		flight.setDepartureTime(new Date());
		flightRepositiory.createFlight(flight);
	}

}
