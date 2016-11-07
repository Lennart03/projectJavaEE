package com.rair.dao;

import org.junit.Before;
import org.junit.Test;

import com.rair.testcore.JpaPersistenceTest;

public class AirlineRepoTest extends JpaPersistenceTest{
	
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
		
	}
	
	@Test
	public void retrieveAirlineFailTest() {
		
	}
	
	@Test
	public void deleteAirlineSuccesTest() {
		
	}
	
	@Test
	public void deleteAirlineFailTest() {
		
	}

}
