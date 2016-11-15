package com.rair.jsfbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
	private Flight returnFlight;
	private Customer customer;
	private Payment paymentChoice = Payment.CREDITCARD;
	private double priceOfBooking;
	private double priceOfReturnBooking;
	private int nSeatsWanted;
	private int nSeatsWantedReturn;
	private TravelingClass selectedTravelClass;
	private TravelingClass selectedReturnTravelClass;
	private double totalPrice;

	@Inject
	private BookingRepository bookingRepository;

	public Flight getFlight() {
		return flight;
	}

	public Flight getReturnFlight() {
		return returnFlight;
	}

	public void setReturnFlight(Flight returnFlight) {
		this.returnFlight = returnFlight;
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
		calculateBookingPrice();
	}

	public void calculateTotalPrice(double priceOfTicket) {

		priceOfBooking = nSeatsWanted * priceOfTicket;
	}

	public Integer maxSeatsOutboundFlight() {
		System.out.println("Flight: " + flight.getFlightNumber());
		System.out.println("Selected travel class: " + selectedTravelClass);
		System.out.println(flight.checkNumberOfSeatsForTravelingClass(selectedTravelClass.toString()));
		return flight.checkNumberOfSeatsForTravelingClass(selectedTravelClass.toString());
	}
	
	public Integer maxNumberOfSeatsReturnFlight() {
		System.out.println("Return flight: " + returnFlight.getFlightNumber());
		System.out.println("Selected travel class: " + selectedReturnTravelClass);
		System.out.println(returnFlight.checkNumberOfSeatsForTravelingClass(selectedReturnTravelClass.toString()));
		return returnFlight.checkNumberOfSeatsForTravelingClass(selectedReturnTravelClass.toString());
	}

	public TravelingClass getSelectedTravelClass() {
		return selectedTravelClass;
	}

	public void setSelectedTravelClass(TravelingClass selectedTravelClass) {
		this.selectedTravelClass = selectedTravelClass;
	}

	public TravelingClass getSelectedReturnTravelClass() {
		return selectedReturnTravelClass;
	}

	public void setSelectedReturnTravelClass(TravelingClass selectedReturnTravelClass) {
		this.selectedReturnTravelClass = selectedReturnTravelClass;
	}

	public void calculatePriceOfBooking(double priceOfTicket) {
		priceOfBooking = nSeatsWanted * priceOfTicket;
		updateTotalPrice();
	}

	private void updateTotalPrice() {
		totalPrice = priceOfBooking + priceOfReturnBooking;
	}

	public void calculatePriceOfReturnBooking(double priceOfReturnTicket) {
		priceOfReturnBooking = nSeatsWantedReturn * priceOfReturnTicket;
		updateTotalPrice();
	}

	public int getnSeatsWantedReturn() {
		return nSeatsWantedReturn;
	}

	public void setnSeatsWantedReturn(int nSeatsWantedReturn) {
		this.nSeatsWantedReturn = nSeatsWantedReturn;
		calculateBookingPrice();
		
	}

	public double getPriceOfReturnBooking() {
		return priceOfReturnBooking;
	}

	public void setPriceOfReturnBooking(double priceOfReturnBooking) {
		this.priceOfReturnBooking = priceOfReturnBooking;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index.xhtml?faces-redirect=true";
	}
	
	public void calculateBookingPrice(){
		this.totalPrice = nSeatsWanted * flight.getTicketPriceByClass(selectedTravelClass.toString()) + nSeatsWantedReturn * returnFlight.getTicketPriceByClass(selectedReturnTravelClass.toString());
	}

}
