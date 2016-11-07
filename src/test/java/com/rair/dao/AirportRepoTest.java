package com.rair.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rair.domain.Airport;
import com.rair.domain.Region;
import com.rair.testcore.JpaPersistenceTest;

public class AirportRepoTest extends JpaPersistenceTest{

	AirportRepository airportRepository;
	
	
	@Before
    public void initializeRepository() {
		airportRepository = new AirportRepository();
		airportRepository.em = entityManager();
    }
	
	@Test
	public void airportCanBePersisted() throws Exception{
		Airport airport = new Airport("testNaam", "testLand", Region.EUROPE, "testCode");
		airport = airportRepository.save(airport);
		Assert.assertNotNull(airport);		
	}
	
}
