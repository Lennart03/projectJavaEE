package com.rair.jsfbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirportRepository;
import com.rair.dao.FlightRepository;
import com.rair.domain.Airport;
import com.rair.domain.Flight;

@ManagedBean
@ViewScoped
public class FlightService {

	@Inject
	private FlightRepository flightRepository;
	
	@Inject
	private AirportRepository airportRepository;

	@ManagedProperty("#{airportServiceBean}")
	private AirportServiceBean airportServiceBean;
	
	private List<Flight> futureFlightsByAirline;
	private List<Airport> airports;


	@PostConstruct
	public void init(){
		airports = airportRepository.findAll();
		futureFlightsByAirline = flightRepository.retrieveFutureFlightsByAirline();
		System.out.println("Size of flights list: " + futureFlightsByAirline.size());
	}

	public FlightRepository getFlightRepository() {
		return flightRepository;
	}

	public void setFlightRepository(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getFutureFlightsByAirline() {
		return futureFlightsByAirline;
	}

	public AirportRepository getAirportRepository() {
		return airportRepository;
	}

	public void setAirportRepository(AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}

	public List<Airport> getAirports() {
		return airports;
	}

	public void setAirports(List<Airport> airports) {
		this.airports = airports;
	}

	public void onRowEdit(RowEditEvent event) {
		System.out.println(event.getObject());
		Flight flight = (Flight) event.getObject();
		if(flight.getId()!= null){
			flight = flightRepository.update(flight, flight.getId());
			FacesMessage msg = new FacesMessage("Flight Edited", flight.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			flightRepository.createFlight(flight);
			FacesMessage msg = new FacesMessage("Added new airport", flight.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
     
    public void onRowCancel(RowEditEvent event) {
    	Flight flight = (Flight) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", flight.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deleteFlight(String flightNumber){
    	if(flightNumber!=null && flightNumber != ""){
    	Flight flight = flightRepository.retrieveFlightByFlightNumber(flightNumber);
    	flightRepository.remove(flight.getId());
    	FacesMessage msg = new FacesMessage("Flight deleted", flight.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
        init();
    }
    
    public void addAction(){
    	futureFlightsByAirline.add(new Flight());
    }

	public AirportServiceBean getAirportServiceBean() {
		return airportServiceBean;
	}

	public void setAirportServiceBean(AirportServiceBean airportServiceBean) {
		this.airportServiceBean = airportServiceBean;
	}

    
    
}
