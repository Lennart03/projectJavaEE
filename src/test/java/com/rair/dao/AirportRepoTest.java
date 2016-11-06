package com.rair.dao;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import com.rair.domain.Airport;
import com.rair.domain.Region;

public class AirportRepoTest {

	@Inject
	AirportRepository service;
	
	@Test
	public void airportCanBePersisted() throws Exception{
		Airport airport = new Airport("testNaam", "testLand", Region.EUROPE, "testCode");
		airport = service.save(airport);
		Assert.assertNotNull(airport);		
	}
	
}
