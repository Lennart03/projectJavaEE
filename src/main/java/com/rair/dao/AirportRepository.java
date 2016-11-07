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

    public Airport save(Airport airport) {
        em.persist(airport);
        return airport;
    }

    public Airport findById(Long id) {
        return em.find(Airport.class, id);
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
    	oldAirport = airport;
    	em.merge(oldAirport);
    	return oldAirport;
    }

}
