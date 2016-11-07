package com.rair.dao;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;

import com.rair.domain.Airline;
import com.rair.testcore.JpaPersistenceTest;

public class AirlineRepoTest extends JpaPersistenceTest{
	
	private static final Long FAIL_AIRLINE_ID = 666L;
	
	AirlineRepository airlineRepository;
	
	@Before
	public void init() {
		airlineRepository = new AirlineRepository();
		airlineRepository.entityManager = entityManager();
	}
	
	@Test
	public void createAirlineTest(){
		airlineRepository.createAirline("Test airline");
	}
	
	@Test
	public void retrieveAirlineSuccesTest() {
		Airline retrieveAirline = airlineRepository.retrieveAirline("Air Botswana");
		System.out.println(retrieveAirline);
		assertEquals("Air Botswana", retrieveAirline.getName());
		Airline airline = airlineRepository.entityManager.find(Airline.class, 3L);
		System.out.println(airline);
		assertEquals("Brussels Airlines", airline.getName());
		
	}
	
	@Test(expected = NoResultException.class)
	public void retrieveAirlineFailTest() throws Exception{
		airlineRepository.retrieveAirline("This should fail airport");
		airlineRepository.entityManager.find(Airline.class, FAIL_AIRLINE_ID);
		
	}
	
	@Test
	public void deleteAirlineSuccesTest() {
		assertTrue(airlineRepository.deleteAirline(new Long(6)));
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void deleteAirlineFailTest() throws Exception{
		airlineRepository.deleteAirline(FAIL_AIRLINE_ID);
		
	}

}
