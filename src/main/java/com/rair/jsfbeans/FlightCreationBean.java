package com.rair.jsfbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.rair.dao.AirlineRepository;
import com.rair.domain.Airline;
import com.rair.domain.Airport;
import com.rair.domain.Booking;
import com.rair.domain.Region;
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

	private List<Airline> airlines = new ArrayList<>();
	private List<Airline> filteredAirlines = new ArrayList<>();
	private List<Region> regions = Arrays.asList(Region.values());
	private Region defaultRegion;

	@Inject
	private AirlineRepository airlineRepository;

	@PostConstruct
	public void init() {
		this.airlines = airlineRepository.retrieveAllAirports();
		defaultRegion = regions.get(0);
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

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		System.out.println("Airline selected: " +airline.getName());
		this.airline = airline;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public Map<TravelingClass, Integer> getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Map<TravelingClass, Integer> numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Map<TravelingClass, Integer> getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Map<TravelingClass, Integer> availableSeats) {
		this.availableSeats = availableSeats;
	}

	public Airport getDepartureDestination() {
		return departureDestination;
	}

	public void setDepartureDestination(Airport departureDestination) {
		this.departureDestination = departureDestination;
	}

	public Airport getArrivalDestination() {
		return arrivalDestination;
	}

	public void setArrivalDestination(Airport arrivalDestination) {
		this.arrivalDestination = arrivalDestination;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public List<Airline> getAirlines() {
		return airlines;
	}

	public void setAirlines(List<Airline> airlines) {
		this.airlines = airlines;
	}

	public List<Airline> getFilteredAirlines() {
		return filteredAirlines;
	}

	public void setFilteredAirlines(List<Airline> filteredAirlines) {
		this.filteredAirlines = filteredAirlines;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public Region getDefaultRegion() {
		return defaultRegion;
	}

	public void setDefaultRegion(Region defaultRegion) {
		this.defaultRegion = defaultRegion;
	}
	
	
}
