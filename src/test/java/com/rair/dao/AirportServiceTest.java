package com.rair.dao;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.rair.domain.Airport;
import com.rair.domain.Region;

public class AirportServiceTest {

	@Inject
	AirportService service;
	
	@Test
	public void airportCanBePersisted() throws Exception{
		Airport airport = service.createAirport("testNaam", "testLand", Region.EUROPE, "testCode");
		Assert.assertNotNull(airport);		
	}
	
}
