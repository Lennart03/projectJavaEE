package com.rair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Airline;
import com.rair.domain.Flight;

@Stateless
public class AirlineRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	public Airline createAirline(String name) {
		Airline airline = new Airline(name);
		entityManager.persist(airline);
		return airline;
	}
	
	public boolean deleteAirline(Long id) {
		List<Flight> conflictingFlights = entityManager.createQuery("select f from Flight f where f.airline.id = " + id , Flight.class).getResultList();
    	for(Flight flight:conflictingFlights){
    		entityManager.remove(entityManager.getReference(Flight.class, flight.getId()));
    	}
    	entityManager.remove(entityManager.getReference(Airline.class, id));
		return true;
	}
	
	public Airline retrieveAirline(String name) {
		Airline airline = (Airline) entityManager.createQuery("SELECT a FROM Airline a WHERE a.name= '"+ name+"'").getSingleResult();
		return airline;
	}
	
	public Airline retrieveAirline(Long id) {
		Airline airline = (Airline) entityManager.createQuery("SELECT a FROM Airline a WHERE a.id= '"+ id+"'").getSingleResult();
		return airline;
	}
	
	public List<Airline> retrieveAllAirlines() {
		List<Airline> airlines = entityManager.createQuery("Select airline from Airline airline", Airline.class).getResultList();
		System.out.println(airlines);
		return airlines;
	}
	
	public void saveAirline(Airline airline) {
		entityManager.merge(airline);
	}
	
	public Airline update(Airline airline, Long airlineId){
    	Airline oldAirline = entityManager.find(Airline.class, airlineId);
    	System.out.println("Oude maatschappij naam: " + oldAirline.getName());
    	System.out.println("Nieuwe maatschappij naam: " + airline.getName());
    	oldAirline = airline;
    	System.out.println("Te mergen luchthaven naam: " + oldAirline.getName());
    	entityManager.merge(oldAirline);
    	return oldAirline;
    }
	
}
