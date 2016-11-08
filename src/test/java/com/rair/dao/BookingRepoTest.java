package com.rair.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.rair.domain.Booking;
import com.rair.domain.BookingStatus;
import com.rair.domain.Customer;
import com.rair.domain.Payment;
import com.rair.testcore.JpaPersistenceTest;

public class BookingRepoTest extends JpaPersistenceTest {

	BookingRepository bookingRepository;
	CustomerRepository customerRepository;
	List<Customer> customers;

	@Before
	public void init() {
		bookingRepository = new BookingRepository();
		customerRepository = new CustomerRepository();
		customerRepository.em = entityManager();
		customers = customerRepository.findAll();
		bookingRepository.entityManager = entityManager();
		
	}

	@Test
	public void createBookingTest() {
		Booking booking = new Booking();
		booking.setNumberOfSeats(2);
		booking.setPaymentChoice(Payment.CREDITCARD);
		booking.setCustomer(customers.get(0));
		booking.setPrice(250.0);
		booking.setStatus(BookingStatus.INITIATED);
		bookingRepository.createBooking(booking);
		
	}

}
