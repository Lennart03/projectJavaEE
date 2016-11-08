package com.rair.jsfbeans;

import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.rair.dao.FlightRepository;
import com.rair.domain.Flight;

@ManagedBean
@SessionScoped
public class FlightService {

	@Inject
	private FlightRepository flightRepository;
	
	private List<Flight> futurFlightsByAirline;

	@PostConstruct
	public void init() throws ParseException {
		futurFlightsByAirline = flightRepository.retrieveFutureFlightsByAirline();
	}

	public FlightRepository getFlightRepository() {
		return flightRepository;
	}

	public void setFlightRepository(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getFuturFlightsByAirline() {
		return futurFlightsByAirline;
	}

	public void setFuturFlightsByAirline(List<Flight> futurFlightsByAirline) {
		this.futurFlightsByAirline = futurFlightsByAirline;
	}

	
	
	
}
