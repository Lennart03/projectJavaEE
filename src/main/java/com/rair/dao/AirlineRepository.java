package com.rair.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Airline;

public class AirlineRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public void createAirline(String name) {
		Airline airline = new Airline(name);
		entityManager.persist(airline);
	}
	
	public void deleteAirline(Long id) {
		entityManager.remove(entityManager.find(Airline.class, id));
	}
	
	public Airline retrieveAirline(String name) {
		Airline airline = (Airline) entityManager.createQuery("SELECT a FROM Airline a WHERE a.name= "+ name).getSingleResult();
		return airline;
	}
	
	
}
