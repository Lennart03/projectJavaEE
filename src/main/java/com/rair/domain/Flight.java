package com.rair.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Flight implements Serializable {

	private static final long serialVersionUID = 9060103451334852487L;
	private static final double BUSINESSCLASS_MULTIPLIER = 3.75;
	private static final double FIRSTCLASS_MULTIPLIER = 9.5468;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Double basePrice;

	@Column
	@NotBlank
	private String flightNumber;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Airline airline;

	@ElementCollection
	@CollectionTable(name = "number_of_seats")
	@MapKeyColumn(name = "traveling_class")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "number_of_seats")
	private Map<TravelingClass, Integer> numberOfSeats;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "available_seats")
	@MapKeyColumn(name = "traveling_class")
	@Column(name = "available_seats")
	@MapKeyEnumerated(EnumType.STRING)
	private Map<TravelingClass, Integer> availableSeats;

	@OneToOne
	@JoinColumn(nullable = false)
	private Airport departureDestination;

	@OneToOne
	@JoinColumn(nullable = false)
	private Airport arrivalDestination;
	private Date departureTime;

	public Flight() {
		super();
		numberOfSeats = new HashMap<>();
		availableSeats = new HashMap<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Map<TravelingClass, Integer> getNumberOfSeats() {
		return numberOfSeats;
	}

	public void addNumberOfSeatsForClass(TravelingClass travelingClass, Integer nunberOfSeats) {
		numberOfSeats.put(travelingClass, nunberOfSeats);
	}

	public Map<TravelingClass, Integer> getAvailableSeats() {
		return availableSeats;
	}

	public Integer checkSeatsForTravelingClass(TravelingClass travelingClass) {
		System.out.println(travelingClass+ ": "+availableSeats.get(travelingClass));
		return availableSeats.get(travelingClass);
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
	
	public double getTicketPriceEconomyClass() {
		return basePrice;
	}
	
	public double getTicketPriceBusinessClass() {
		return basePrice * BUSINESSCLASS_MULTIPLIER;
	}
	
	public double getTicketPriceFirstClass() {
		return basePrice * FIRSTCLASS_MULTIPLIER;
	}

	@Override
	public String toString() {
		return "Flight with number: " + flightNumber + ", from: " + departureDestination
				+ " to: " + arrivalDestination;
	}
	
	

}
