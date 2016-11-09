package com.rair.jsfbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.rair.domain.Customer;
import com.rair.domain.Flight;
import com.rair.domain.Payment;

@ManagedBean(name="bookingServiceBean")
@SessionScoped
public class BookingServiceBean {

	private Flight flight;
	private Customer customer;
	private Payment paymentChoice;
	
	
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Payment getPaymentChoice() {
		return paymentChoice;
	}
	public void setPaymentChoice(Payment paymentChoice) {
		this.paymentChoice = paymentChoice;
	}
	
	
	
	
}
