package com.rair.dao;

import java.util.List;

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
		Airport airport2 = new Airport("testNaam", "testLand", Region.EUROPE, "testCode");
		airport = airportRepository.save(airport);
		airport = airportRepository.save(airport2);
		assertNotNull(airport);		
	}
	
	@Test
	public void airportCanBeRemoved() throws Exception{
		Airport airport = new Airport("testNaam", "testLand", Region.EUROPE, "testCode");
		Airport airport2 = new Airport("testNaam", "testLand", Region.EUROPE, "testCode");
		airportRepository.save(airport);
		airportRepository.save(airport2);
		airportRepository.remove(2);
		List<Airport> airportList = airportRepository.findAll();
		assertEquals(1, airportList.size());		
	}
	
	@Test
	public void airportCanBeUpdated() throws Exception{
		Airport airport = new Airport("testNaam", "testLand", Region.EUROPE, "testCode");
		Airport airport2 = new Airport("testNaam", "testLand", Region.EUROPE, "testCode");
		airportRepository.save(airport);
		airportRepository.save(airport2);
		airport2.setName("testAangepast");
		airport2 = airportRepository.update(airport2, airport2.getId());
		assertEquals("testAangepast",airport2.getName());
	}
	
}
