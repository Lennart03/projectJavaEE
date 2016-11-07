package com.rair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rair.domain.Airport;

@Stateless
public class AirportRepository {
	private Logger logger = LoggerFactory.getLogger(getClass());

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
    
    public Airport update(Airport airport, Integer airportId){
    	return null;
    }

}
