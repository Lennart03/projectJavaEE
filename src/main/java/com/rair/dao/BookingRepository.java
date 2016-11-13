package com.rair.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Booking;
import com.rair.domain.Customer;
import com.rair.domain.Payment;

public class BookingRepository {

	@PersistenceContext
	EntityManager entityManager;

	private static final String QUERY_START = "SELECT b FROM Booking b";

	public void createBooking(Booking booking) {
		entityManager.persist(booking);
	}

	public boolean deleteBooking(Booking booking) {
		entityManager.remove(booking);
		return true;
	}

	public List<Booking> retrieveAllBookings() {
		List<Booking> bookings = entityManager.createQuery(QUERY_START, Booking.class).getResultList();
		return bookings;
	}

	public List<Booking> retrieveBookingsFromCustomer(Customer customer) {
		List<Booking> bookings = entityManager
				.createQuery(QUERY_START + " WHERE customer_id = '" + customer.getId() + "'", Booking.class)
				.getResultList();
		return bookings;
	}

	public List<Booking> retrieveBookingFromCustomerByName(String firstName, String lastName) {
		Customer customer = (Customer) entityManager
				.createQuery(QUERY_START + " firstName = '" + firstName + "' and lastName = '" + lastName + "'")
				.getSingleResult();
		return retrieveBookingsFromCustomer(customer);
	}
	
	public List<Booking> retrieveBookingFromFlightId(Long flightID){
		List<Booking> bookings = entityManager
				.createQuery(QUERY_START + " WHERE flight_id = '" + flightID + "'", Booking.class)
				.getResultList();
		return bookings;
	}
	
	public List<Booking> retrieveBookingFromFlightByTravelingClass(String type, Long flightID){
		List<Booking> bookings = entityManager
				.createQuery(QUERY_START + " WHERE flight_id = '" + flightID + "' AND travelingClass = '" + type + "'", Booking.class)
				.getResultList();
		return bookings;
	}

	public List<Booking> retrieveAllBookingsByCreditCard() {
		List<Booking> bookings = entityManager.createQuery(QUERY_START+"WHERE paymentChoice ='"+Payment.CREDITCARD+"'", Booking.class).getResultList();
		return bookings;
	}

}
