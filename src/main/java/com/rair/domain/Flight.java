package com.rair.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Double basePrice;
	private String flightNumber;
//	private Airline airline;
	private List<Booking> bookings;
	private HashMap<TravelingClass, Integer> numberOfSeats;
	private HashMap<TravelingClass, Integer> availableSeats;
//	private Airport departureDestination;
//	private Airport arrivalDestination;
	private Date departureTime;
	
	public Flight() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

//	public Airline getAirline() {
//		return airline;
//	}
//
//	public void setAirline(Airline airline) {
//		this.airline = airline;
//	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public HashMap<TravelingClass, Integer> getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(HashMap<TravelingClass, Integer> numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public HashMap<TravelingClass, Integer> getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(HashMap<TravelingClass, Integer> availableSeats) {
		this.availableSeats = availableSeats;
	}

//	public Airport getDepartureDestination() {
//		return departureDestination;
//	}
//
//	public void setDepartureDestination(Airport departureDestination) {
//		this.departureDestination = departureDestination;
//	}
//
//	public Airport getArrivalDestination() {
//		return arrivalDestination;
//	}
//
//	public void setArrivalDestination(Airport arrivalDestination) {
//		this.arrivalDestination = arrivalDestination;
//	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	
	
	
	

}
