package com.rair.jsfbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.rair.dao.BookingRepository;
import com.rair.domain.Booking;
import com.rair.domain.BookingStatus;
import com.rair.domain.Customer;
import com.rair.domain.Flight;
import com.rair.domain.Payment;
import com.rair.domain.TravelingClass;

@ManagedBean(name = "bookingServiceBean")
@SessionScoped
public class BookingServiceBean {

	private Flight flight;
	private Customer customer;
	private Payment paymentChoice = Payment.CREDITCARD;
	private double priceOfBooking;
	private int nSeatsWanted;
	private TravelingClass selectedTravelClass;

	@Inject
	private BookingRepository bookingRepository;

	public Flight getFlight() {
		return flight;
	}

	public BookingRepository getBookingRepository() {
		return bookingRepository;
	}

	public void setBookingRepository(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public void setFlight(Flight flight) {
		System.out.println("Flight is set: " + flight);
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
		System.out.println("Payment choice: " + paymentChoice);
		this.paymentChoice = paymentChoice;
	}

	public double getPriceOfBooking() {
		return priceOfBooking;
	}

	public void setPriceOfBooking(double priceOfBooking) {
		this.priceOfBooking = priceOfBooking;
	}

	public int getnSeatsWanted() {
		return nSeatsWanted;
	}

	public void setnSeatsWanted(int nSeatsWanted) {
		this.nSeatsWanted = nSeatsWanted;
	}

	public void calculateTotalPrice(double priceOfTicket) {

		priceOfBooking = nSeatsWanted * priceOfTicket;
	}

	public Integer maximumSeatsForClass() {
		return flight.checkSeatsForTravelingClass(selectedTravelClass);
	}

	public TravelingClass getSelectedTravelClass() {
		return selectedTravelClass;
	}

	public void setSelectedTravelClass(TravelingClass selectedTravelClass) {
		this.selectedTravelClass = selectedTravelClass;
	}

	public void calculatePriceOfBooking(double priceOfTicket) {
		priceOfBooking = nSeatsWanted * priceOfTicket;
	}

	public String getTravelingClassAsString() {
		String str = "";
		switch (selectedTravelClass) {
		case ECONOMY:
			str += "Economy";
			break;
		case BUSINESS:
			str += "Business";
			break;
		default:
			str += "First Class";
			break;
		}
		return str;
	}

	public boolean isCreditCardPayment() {
		return paymentChoice.equals(Payment.CREDITCARD);
	}

	public String makeBooking() {
		Booking booking = new Booking();
		System.out.println(customer);
		booking.setCustomer(customer);
		booking.setFlight(flight);
		booking.setPrice(priceOfBooking);
		booking.setNumberOfSeats(nSeatsWanted);
		booking.setTravelingClass(selectedTravelClass);
		booking.setPaymentChoice(paymentChoice);
		if (paymentChoice.equals(Payment.CREDITCARD)) {
			booking.setStatus(BookingStatus.PAYMENT_SUCCES);
		} else {
			booking.setStatus(BookingStatus.PAYMENT_PENDING);
		}
		bookingRepository.createBooking(booking);
		return "index.xhtml?faces-redirect=true";
	}

}
