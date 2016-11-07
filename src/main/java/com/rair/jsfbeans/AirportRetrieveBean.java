package com.rair.jsfbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.rair.dao.AirportRepository;
import com.rair.domain.Airport;

@Stateless
@LocalBean
public class AirportRetrieveBean{
	
	@EJB
	AirportRepository airportRepo;
	
	private List<Airport> airports = new ArrayList<Airport>();

	public List<Airport> getAirports() {
		this.airports = airportRepo.findAll();
		return airports;
	}
	

}
