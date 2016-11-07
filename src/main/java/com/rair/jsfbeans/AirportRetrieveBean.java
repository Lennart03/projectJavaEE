package com.rair.jsfbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.rair.dao.AirportRepository;
import com.rair.domain.Airport;

@ManagedBean
@SessionScoped
public class AirportRetrieveBean{
	
	@Inject
	AirportRepository airportRepo;
		
	private List<Airport> airports = new ArrayList<Airport>();

	public List<Airport> getAirports() {
		System.out.println("functie aangeroepen");
		this.airports = airportRepo.findAll();
		return airports;
	}
	

}
