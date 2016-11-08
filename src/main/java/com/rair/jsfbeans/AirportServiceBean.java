package com.rair.jsfbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.rair.dao.AirportRepository;
import com.rair.domain.Airport;

@ManagedBean(name="airportServiceBean")
@ApplicationScoped
public class AirportServiceBean implements Serializable{

	private static final long serialVersionUID = -3502453464819265169L;

	private List<Airport> airports;
	
	@Inject
	private AirportRepository airportRepository;
	
	@PostConstruct
	public void init() {
		airports = airportRepository.findAll();
	}

	public List<Airport> getAirports() {
		return airports;
	}
	
	public Airport findAirportByName(String airportName) {
		return airportRepository.findByAirportName(airportName);
	}
	
}
