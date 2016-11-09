package com.rair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Airport;
import com.rair.domain.Flight;

@Stateless
public class AirportRepository {

	@PersistenceContext
    EntityManager em;
	
	public AirportRepository() {
		// TODO Auto-generated constructor stub
	}

    public Airport save(Airport airport) {
        em.persist(airport);
        return airport;
    }

    public Airport findById(Long id) {
        return em.find(Airport.class, id);
    }
    
    public Airport findByAirportName(String airportName) {
    	return em.createQuery("select a from Airport a where a.name = '"+airportName+"'", Airport.class).getSingleResult();
    }
    
    public Airport findByAirportCode(String airportCode) {
    	return em.createQuery("select a from Airport a where a.airportCode = '"+airportCode+"'", Airport.class).getSingleResult();
    }

    public List<Airport> findAll() {
        return em.createQuery("select a from Airport a", Airport.class).getResultList();
    }

    public void remove(long airportId) {
    	List<Flight> conflictingFlights = em.createQuery("select f from Flight f where f.departureDestination.id = " + airportId +"or f.arrivalDestination.id = " + airportId + "", Flight.class).getResultList();
    	for(Flight flight:conflictingFlights){
    		em.remove(em.getReference(Flight.class, flight.getId()));
    	}
        em.remove(em.getReference(Airport.class, airportId));
    }
    
    public Airport update(Airport airport, Long airportId){
    	Airport oldAirport = em.find(Airport.class, airportId);
    	System.out.println("Oude luchthaven naam: " + oldAirport.getName());
    	System.out.println("Nieuwe luchthaven naam: " + airport.getName());
    	oldAirport = airport;
    	System.out.println("Te mergen luchthaven naam: " + oldAirport.getName());
    	em.merge(oldAirport);
    	return oldAirport;
    }

}
