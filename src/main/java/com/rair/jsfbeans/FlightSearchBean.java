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
import com.rair.domain.Flight;
import com.rair.domain.TravelingClass;

@ManagedBean
@ViewScoped
public class FlightSearchBean implements Serializable {

	private static final long serialVersionUID = 8792693292832076782L;

	@ManagedProperty("#{airportServiceBean}")
	private AirportServiceBean airportServiceBean;

	@Inject
	private FlightRepository flightRepository;

	private Airport arrivalAirport;
	private Flight selectedFlight;
	private List<Flight> flightsForArrival;

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
		FacesMessage msg = new FacesMessage("Flight Selected", ((Flight) event.getObject()).getFlightNumber());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Flight Unselected", ((Flight) event.getObject()).getFlightNumber());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
