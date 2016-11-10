package com.rair.jsfbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.rair.dao.FlightRepository;
import com.rair.domain.Airport;
import com.rair.domain.Employee;
import com.rair.domain.Flight;
import com.rair.domain.TravelingClass;

@ManagedBean
@ViewScoped
public class FlightSearchBean implements Serializable {

	private static final long serialVersionUID = 8792693292832076782L;

	@ManagedProperty("#{airportServiceBean}")
	private AirportServiceBean airportServiceBean;

	@ManagedProperty("#{bookingServiceBean}")
	private BookingServiceBean bookingServiceBean;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@Inject
	private FlightRepository flightRepository;

	private Airport arrivalAirport;
	private List<Flight> flightsForArrival;
	
	
	private double priceOfTicket;
	

	private String customerID;

	

	public double getPriceOfTicket() {
		return priceOfTicket;
	}

	public void setPriceOfTicket(double priceOfTicket) {
		this.priceOfTicket = priceOfTicket;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public List<Airport> completeAirports(String query) {
		List<Airport> filteredAirports = new ArrayList<>();
		for (Airport airport : airportServiceBean.getAirports()) {
			if (airport.getCountry().toLowerCase().contains(query.toLowerCase())
					|| airport.getCity().toLowerCase().contains(query.toLowerCase())) {
				filteredAirports.add(airport);
			}
		}
		return filteredAirports;
	}

	public AirportServiceBean getAirportServiceBean() {
		return airportServiceBean;
	}

	public void setAirportServiceBean(AirportServiceBean airportServiceBean) {
		this.airportServiceBean = airportServiceBean;
	}

	public BookingServiceBean getBookingServiceBean() {
		return bookingServiceBean;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void setBookingServiceBean(BookingServiceBean bookingServiceBean) {
		this.bookingServiceBean = bookingServiceBean;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public FlightRepository getFlightRepository() {
		return flightRepository;
	}

	public void setFlightRepository(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getFlightsForArrival() {
		return flightsForArrival;
	}

	public void setFlightsForArrival(List<Flight> flightsForArrival) {
		this.flightsForArrival = flightsForArrival;
	}

	public String onFlowProcess(FlowEvent event) {
		if (event.getNewStep().equals("flights")) {
			if (event.getOldStep().equals("ticketChoice")) {
				bookingServiceBean.setFlight(null);
				bookingServiceBean.setnSeatsWanted(0);
				bookingServiceBean.setPriceOfBooking(0);
				bookingServiceBean.setSelectedTravelClass(TravelingClass.ECONOMY);
			}
			flightsForArrival = flightRepository.retrieveFlightsByDestination(arrivalAirport);
			for (Flight flight : flightsForArrival) {
				System.out.println(flight.getFlightNumber());
				for (Entry<TravelingClass, Integer> entry : flight.getAvailableSeats().entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}
			}
		}
		return event.getNewStep();
	}

	public void onRowSelect(SelectEvent event) {
		bookingServiceBean.setFlight((Flight) event.getObject());
		priceOfTicket = bookingServiceBean.getFlight().getBasePrice() * Employee.RAIR_PERCENTAGE;
		bookingServiceBean.setSelectedTravelClass(TravelingClass.ECONOMY);
		FacesMessage msg = new FacesMessage("Flight Selected");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		bookingServiceBean.setFlight(null);
		FacesMessage msg = new FacesMessage("Flight Unselected");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String bookFlight() {
		
		System.out.println(loginBean.getPerson());
		if(loginBean.getPerson() == null) {
			return "toLogin";
		}
		
		return "toLogin";
	}

	public void changePriceOfOneTicket() {
		switch (bookingServiceBean.getSelectedTravelClass()) {
		case BUSINESS:
			priceOfTicket = bookingServiceBean.getFlight().getTicketPriceBusinessClass();
			break;
		case FIRST_CLASS:
			priceOfTicket = bookingServiceBean.getFlight().getTicketPriceFirstClass();
			break;
		default:
			priceOfTicket = bookingServiceBean.getFlight().getTicketPriceEconomyClass();
			break;
		}
		priceOfTicket *= Employee.RAIR_PERCENTAGE;
	}


	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

}
