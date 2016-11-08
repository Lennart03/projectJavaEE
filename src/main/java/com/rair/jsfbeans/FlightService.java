package com.rair.jsfbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.rair.dao.FlightRepository;
import com.rair.domain.Flight;

@ManagedBean
@ViewScoped
public class FlightService {

	@Inject
	private FlightRepository flightRepository;
	
	private List<Flight> futurFlightsByAirline;

	@PostConstruct
	public void init(){
		futurFlightsByAirline = flightRepository.retrieveFutureFlightsByAirline();
		System.out.println("Size of flights list: " + futurFlightsByAirline.size());
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

	
	
	
}
