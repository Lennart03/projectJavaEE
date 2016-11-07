package com.rair.jsfbeans;

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

	public List<Airport> findAll() {
		return airportRepo.findAll();
	}
	

}
