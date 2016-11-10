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

	@Inject
	private FlightRepository flightRepository;

	private Airport arrivalAirport;
	private Flight selectedFlight;
	private List<Flight> flightsForArrival;
	private TravelingClass selectedTravelClass;
	private int nSeatsWanted;
	private double priceOfTicket;
	private double priceOfBooking;
	
	private String customerID;
	

	public TravelingClass getSelectedTravelClass() {
		return selectedTravelClass;
	}

	public double getPriceOfTicket() {
		return priceOfTicket;
	}

	public void setPriceOfTicket(double priceOfTicket) {
		this.priceOfTicket = priceOfTicket;
	}

	public void setSelectedTravelClass(TravelingClass selectedTravelClass) {
		System.out.println(selectedTravelClass);
		this.selectedTravelClass = selectedTravelClass;
	}

	public int getnSeatsWanted() {
		return nSeatsWanted;
	}

	public void setnSeatsWanted(int nSeatsWanted) {
		this.nSeatsWanted = nSeatsWanted;
	}

	public double getPriceOfBooking() {
		return priceOfBooking;
	}

	public void setPriceOfBooking(double priceOfBooking) {
		this.priceOfBooking = priceOfBooking;
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

	public Flight getSelectedFlight() {
		return selectedFlight;
	}

	public void setSelectedFlight(Flight selectedFlight) {
		this.selectedFlight = selectedFlight;
	}

	public String onFlowProcess(FlowEvent event) {
		if (event.getNewStep().equals("flights")) {
			if (event.getOldStep().equals("ticketChoice")) {
				selectedFlight = null;
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
		selectedFlight = ((Flight) event.getObject());
		priceOfTicket = selectedFlight.getBasePrice() * Employee.RAIR_PERCENTAGE;
		selectedTravelClass = TravelingClass.ECONOMY;
		FacesMessage msg = new FacesMessage("Flight Selected");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		selectedFlight = null;
		FacesMessage msg = new FacesMessage("Flight Unselected");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String bookFlight() {
		bookingServiceBean.setFlight(selectedFlight);
		return "toLogin";
	}

	public Integer maximumSeats() {
		return selectedFlight.checkSeatsForTravelingClass(selectedTravelClass);
	}

	public void changePriceOfOneTicket() {
		switch (selectedTravelClass) {
		case BUSINESS:
			priceOfTicket = selectedFlight.getTicketPriceBusinessClass();
			break;
		case FIRST_CLASS:
			priceOfTicket = selectedFlight.getTicketPriceFirstClass();
			break;
		default:
			priceOfTicket = selectedFlight.getTicketPriceEconomyClass();
			break;
		}
		priceOfTicket *= Employee.RAIR_PERCENTAGE;
	}

	public void calculateTotalPrice() {
		priceOfBooking = nSeatsWanted * priceOfTicket;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	

}
