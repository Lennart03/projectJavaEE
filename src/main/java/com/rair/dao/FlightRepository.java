package com.rair.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Airport;
import com.rair.domain.Booking;
import com.rair.domain.Flight;

@Stateless
public class FlightRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Inject
	BookingRepository bookingRepository;

	private static final String QUERY_START = "SELECT f FROM Flight f ";

	public BookingRepository getBookingRepository() {
		return bookingRepository;
	}

	public void setBookingRepository(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public void createFlight(Flight flight) {
		entityManager.persist(flight);
	}

	public void deleteFlight(Flight flight) {
		entityManager.remove(flight);
	}

	public Flight retrieveFlightByFlightNumber(String flightNumber) {
		Flight flight = entityManager
				.createQuery(QUERY_START + "WHERE f.flightNumber = '" + flightNumber + "'", Flight.class)
				.getSingleResult();
		return flight;
	}

	public List<Flight> retrieveAllFlights() {
		List<Flight> flights = entityManager.createQuery(QUERY_START, Flight.class).getResultList();
		return flights;
	}

	public List<Flight> retrieveFlightsByDestination(Airport airport) {
		List<Flight> flights = entityManager
				.createQuery(QUERY_START + "where f.arrivalDestination.id = " + airport.getId(), Flight.class)
				.getResultList();
		return flights;
	}

	public List<Flight> retrieveFlightsFromAndTo(Airport departure, Airport arrival) {
		List<Flight> flights = entityManager.createQuery(QUERY_START + "where f.arrivalDestination.id = "
				+ arrival.getId() + " and f.departureDestination.id = " + departure.getId(), Flight.class)
				.getResultList();
		return flights;
	}

	public List<Flight> retrieveFutureFlightsByAirline(Long airlineID) {
		Date date2 = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		List<Flight> flights = entityManager
				.createQuery(QUERY_START + "where f.airline.id = " + airlineID, Flight.class).getResultList();
		List<Flight> futureFlights = new ArrayList<Flight>();
		for (Flight f : flights) {

			Date date1 = f.getDepartureTime();

			System.out.println(date1.toString());
			System.out.println(date2.toString());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date1);

			if (cal1.after(cal2)) {
				futureFlights.add(f);
			}
		}
		return futureFlights;
	}

	public List<Flight> retrieveFlightsOnDate(Date date) {
		List<Flight> flights = entityManager
				.createQuery(QUERY_START + "WHERE departureTime ='" + date + "'", Flight.class).getResultList();
		return flights;
	}

	public List<Flight> retrieveFlightsSortedOnPrice() {
		List<Flight> flights = retrieveAllFlights();
		flights.sort(new Comparator<Flight>() {

			@Override
			public int compare(Flight flight1, Flight flight2) {
				return flight1.getBasePrice().compareTo(flight2.getBasePrice());
			}
		});
		return flights;
	}

	public List<Flight> retrieveFlightsSortedOnDepartureTime() {
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

	public Flight update(Flight flight, Long flightId) {
		Flight oldFlight = entityManager.find(Flight.class, flightId);
		oldFlight = flight;
		entityManager.merge(oldFlight);
		return oldFlight;
	}

	public void remove(long flightId) {
		List<Booking> conflictingBookings = bookingRepository.retrieveBookingFromFlightId(flightId);
		for (Booking b : conflictingBookings) {
			entityManager.remove(entityManager.getReference(Booking.class, b.getId()));
		}
		entityManager.remove(entityManager.getReference(Flight.class, flightId));
	}
}
