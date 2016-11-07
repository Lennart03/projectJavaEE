package com.rair.dao;


import org.junit.Before;
import org.junit.Test;

import com.rair.domain.Airline;
import com.rair.domain.Partner;
import com.rair.testcore.JpaPersistenceTest;

public class PartnerRepoTest extends JpaPersistenceTest{
	
	PartnerRepository partnerRepository;
	
	
	@Before
    public void initializeRepository() {
		partnerRepository = new PartnerRepository();
		partnerRepository.em = entityManager();
    }
	
	@Test
	public void partnerCanBePersisted() throws Exception{
		Partner partner = new Partner("captain","jack","captain@jack.com","blablabla",new Airline("SN Brussels Airlines"));
		partner = partnerRepository.save(partner);
		assertNotNull(partner);		
	}
	
//	@Test
//	public void airportCanBeRemoved() throws Exception{
//		//do something		
//	}
//	
//	@Test
//	public void airportCanBeUpdated() throws Exception{
//		//do something
//	}

}
