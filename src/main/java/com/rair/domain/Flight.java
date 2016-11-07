package com.rair.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private Double basePrice;

	@Column
	@NotBlank
	private String flightNumber;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(nullable=false)
	private Airline airline;

	@ManyToMany
	@JoinTable(name = "jnd_flight_booking", joinColumns = @JoinColumn(name = "flight_fk"), inverseJoinColumns = @JoinColumn(name = "booking_fk"))
	private List<Booking> bookings;

	@ElementCollection
	@CollectionTable(name = "number_of_seats")
	@MapKeyColumn(name = "traveling_class")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "number_of_seats")
	private Map<TravelingClass, Integer> numberOfSeats;
	
	@ElementCollection
	@CollectionTable(name = "available_seats")
	@MapKeyColumn(name = "traveling_class")
	@Column(name = "available_seats")
	@MapKeyEnumerated(EnumType.STRING)
	private Map<TravelingClass, Integer> availableSeats;
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn( nullable = false)
	private Airport departureDestination;
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(nullable = false)
	private Airport arrivalDestination;
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

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
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

}
