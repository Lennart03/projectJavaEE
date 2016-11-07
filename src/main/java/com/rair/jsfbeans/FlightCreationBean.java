package com.rair.jsfbeans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.rair.domain.Airline;
import com.rair.domain.Airport;
import com.rair.domain.Booking;
import com.rair.domain.TravelingClass;

@ManagedBean
@SessionScoped
public class FlightCreationBean {
	
	private Double basePrice;
	private String flightNumber;
	private Airline airline;
	private List<Booking> bookings;
	private Map<TravelingClass, Integer> numberOfSeats;
	private Map<TravelingClass, Integer> availableSeats;
	private Airport departureDestination;
	private Airport arrivalDestination;
	private Date departureTime;
	
	

}
