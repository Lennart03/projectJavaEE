package com.rair.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

	FlightRepository flightRepositiory;
	AirlineRepository airlineRepository;
	AirportRepository airportRepository;
	Flight flight;
	Map<TravelingClass, Integer> numberOfSeats = new HashMap<>();
	Map<TravelingClass, Integer> availableSeats = new HashMap<>();
	Airport departureDestination = new Airport("Cairo International Airport", "Egypt", "Cairo", Region.AFRICA, "CAI");
	Airport arrivalDestination = new Airport("Adolfo Suárez Madrid–Barajas Airport", "Spain", "Madrid",Region.EUROPE, "MAD");
	
	@Before
	public void init() {
		flightRepositiory = new FlightRepository();
		flightRepositiory.entityManager = entityManager();
		
		airlineRepository = new AirlineRepository();
		airlineRepository.entityManager = entityManager();
		
		airportRepository = new AirportRepository();
		airportRepository.em = entityManager();
		
		airportRepository.save(departureDestination);
		airportRepository.save(arrivalDestination);
		

		numberOfSeats.put(TravelingClass.ECONOMY, 100);
		numberOfSeats.put(TravelingClass.BUSINESS, 100);
		numberOfSeats.put(TravelingClass.FIRST_CLASS, 50);

		availableSeats.put(TravelingClass.ECONOMY, 75);
		availableSeats.put(TravelingClass.BUSINESS, 50);
		availableSeats.put(TravelingClass.FIRST_CLASS, 5);

		flight = new Flight();
		flight.setBasePrice(100.0);
		flight.setFlightNumber("flightNumberTest");
		flight.addNumberOfSeatsForClass(TravelingClass.BUSINESS, 100);
		flight.addNumberOfSeatsForClass(TravelingClass.ECONOMY, 100);
		flight.addNumberOfSeatsForClass(TravelingClass.FIRST_CLASS, 50);
		flight.setAvailableSeats(availableSeats);
	}

	@Test
	public void persitFlightTest() {
		Airline a = airlineRepository.createAirline("KLM");
		flight.setAirline(a);
		flight.setFlightNumber("testflight");
		flight.setDepartureDestination(departureDestination);
		flight.setArrivalDestination(arrivalDestination);
		flight.setDepartureTime(new Date());
		flightRepositiory.createFlight(flight);
	}
	
	@Test
	public void deleteFlightWithoutBookingsTest() {
		Flight flight = flightRepositiory.retrieveFlightByFlightNumber("flightNumber_1");
		flightRepositiory.deleteFlight(flight);
	}
	
	@Test
	public void retrieveFlightByFlightNumberTest() {
		Flight flight = flightRepositiory.retrieveFlightByFlightNumber("flightNumber_2");
		assertEquals("flightNumber_2", flight.getFlightNumber());
	}
	
	@Test
	public void retrieveAllFlightsTest() {
		List<Flight> flights = flightRepositiory.retrieveAllFlights();
		assertEquals(14, flights.size());
	}
	
	@Test
	public void retrieveSortedTest() {
		List<Flight> flights = flightRepositiory.retrieveFlightsSortedOnPrice();
		System.out.println("Flights sorted On price");
		for (Flight flight : flights) {
			System.out.println(flight.getFlightNumber() + ": "+flight.getBasePrice());
		}
		System.out.println("\nFlights sorted on departure time");
		flights = flightRepositiory.retrieveFlightsSortedOnDepartureTime();
		for(Flight flight : flights) {
			System.out.println(flight.getFlightNumber()+ ": " +flight.getDepartureTime());
		}
		System.out.println("\nFlights sorted on arrival destionation");
		flights = flightRepositiory.retieveFlightsSortedOnAirport();
		for(Flight flight : flights) {
			System.out.println(flight.getFlightNumber() + ": " + flight.getArrivalDestination().getName());
		}
	}

}
