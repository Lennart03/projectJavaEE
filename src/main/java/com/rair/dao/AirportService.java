package com.rair.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.rair.domain.Airport;
import com.rair.domain.Region;

@Stateless
public class AirportService {

	@PersistenceContext(unitName = "RairPersistenceUnit")
	EntityManager em;

	@Transactional
	public Airport createAirport(String name, String country, Region region, String airportCode) throws Exception{
		Airport airport = new Airport(name, country, region, airportCode);
		em.persist(airport);
		return airport;
	}

}
