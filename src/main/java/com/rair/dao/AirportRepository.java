package com.rair.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Airport;

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
        em.remove(em.getReference(Airport.class, airportId));
    }
    
    public Airport update(Airport airport, Long airportId){
    	Airport oldAirport = em.find(Airport.class, airportId);
    	oldAirport = airport;
    	em.merge(oldAirport);
    	return oldAirport;
    }

}
