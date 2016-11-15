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
import javax.persistence.Transient;

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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "number_of_seats")
	@MapKeyColumn(name = "traveling_class")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "number_of_seats")
	private Map<TravelingClass, Integer> numberOfSeats;

	@ElementCollection(fetch = FetchType.EAGER)
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

	@Transient
	private int economySeats = 0;
	@Transient
	private int businessSeats = 0;
	@Transient
	private int firstClassSeats = 0;

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
		System.out.println("New number of seats for " + travelingClass + " is: " + nunberOfSeats);
		numberOfSeats.put(travelingClass, nunberOfSeats);
	}

	public Map<TravelingClass, Integer> getAvailableSeats() {
		return availableSeats;
	}

	public Integer checkSeatsForTravelingClass(TravelingClass travelingClass) {
		System.out.println(travelingClass + ": " + availableSeats.get(travelingClass));
		System.out.println(availableSeats);
		return availableSeats.get(travelingClass);
	}

	public Integer checkNumberOfSeatsForTravelingClass(String stringClass) {
		TravelingClass type = TravelingClass.valueOf(stringClass);
		if (numberOfSeats.get(type) != null && numberOfSeats.get(type) != 0)
			return numberOfSeats.get(type);
		else {
			return 0;
		}
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
		return basePrice * Employee.RAIR_PERCENTAGE;
	}

	public double getTicketPriceBusinessClass() {
		return basePrice * BUSINESSCLASS_MULTIPLIER * Employee.RAIR_PERCENTAGE;
	}

	public double getTicketPriceFirstClass() {
		return basePrice * FIRSTCLASS_MULTIPLIER * Employee.RAIR_PERCENTAGE;
	}

	@Override
	public String toString() {
		return "Flight with number: " + flightNumber + ", from: " + departureDestination + " to: " + arrivalDestination;
	}

	public int getEconomySeats() {
		return economySeats;
	}

	public void setEconomySeats(int economySeats) {
		this.economySeats = economySeats;
	}

	public int getBusinessSeats() {
		return businessSeats;
	}

	public void setBusinessSeats(int businessSeats) {
		this.businessSeats = businessSeats;
	}

	public int getFirstClassSeats() {
		return firstClassSeats;
	}

	public void setFirstClassSeats(int firstClassSeats) {
		this.firstClassSeats = firstClassSeats;
	}

	public void initializeTransientFields() {
		try {
			economySeats = numberOfSeats.get(TravelingClass.ECONOMY);
		} catch (NullPointerException e) {
			economySeats = 0;
		}
		try {
			businessSeats = numberOfSeats.get(TravelingClass.BUSINESS);
		} catch (NullPointerException e) {
			businessSeats = 0;
		}
		try {
			firstClassSeats = numberOfSeats.get(TravelingClass.FIRST_CLASS);
		} catch (NullPointerException e) {
			firstClassSeats = 0;
		}

	}

	public double getTicketPriceByTravelclass(String travelclass) {
		double ticketPrice = 0;
		switch (travelclass) {
		case "ECONOMY":
			ticketPrice = basePrice * Employee.RAIR_PERCENTAGE;
			break;
		case "BUSINESS":
			ticketPrice = basePrice * BUSINESSCLASS_MULTIPLIER * Employee.RAIR_PERCENTAGE;
			break;
		default:
			ticketPrice = basePrice * FIRSTCLASS_MULTIPLIER * Employee.RAIR_PERCENTAGE;
			break;

		}
		return ticketPrice;
	}

}
