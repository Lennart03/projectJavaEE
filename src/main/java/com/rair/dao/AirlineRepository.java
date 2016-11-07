package com.rair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Airline;
import com.rair.domain.Airport;

@Stateless
public class AirlineRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public void createAirline(String name) {
		Airline airline = new Airline(name);
		entityManager.persist(airline);
	}
	
	public boolean deleteAirline(Long id) {
		entityManager.remove(entityManager.getReference(Airline.class, id));
		return true;
	}
	
	public Airline retrieveAirline(String name) {
		Airline airline = (Airline) entityManager.createQuery("SELECT a FROM Airline a WHERE a.name= '"+ name+"'").getSingleResult();
		return airline;
	}
	
	public List<Airline> retrieveAllAirports() {
		List<Airline> airlines = entityManager.createQuery("Select airline from Airline airline", Airline.class).getResultList();
		System.out.println(airlines);
		return airlines;
	}
	
	public void saveAirline(Airline airline) {
		entityManager.merge(airline);
	}
	
	
}
