package com.rair.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Flight flight;
	
	@Column
	private Integer numberOfSeats;
	
	@Enumerated(EnumType.STRING)
	private TravelingClass travelingClass;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	
	@Enumerated(EnumType.STRING)
	private Payment paymentChoice;
	
	@Column
	private Double price;
	
	public Booking() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public Payment getPaymentChoice() {
		return paymentChoice;
	}

	public void setPaymentChoice(Payment paymentChoice) {
		this.paymentChoice = paymentChoice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public TravelingClass getTravelingClass() {
		return travelingClass;
	}

	public void setTravelingClass(TravelingClass travelingClass) {
		this.travelingClass = travelingClass;
	}
	
	
}
