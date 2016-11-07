package com.rair.dao;


import java.util.List;

import javax.validation.ConstraintViolationException;

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
	
	@Test(expected=ConstraintViolationException.class)
	public void partnerCanBePersistedWithoutFirstName() throws Exception{
		Partner partner = new Partner();
		partner.setLastName("Captain");
		partner.setAirline(new Airline("test"));
		partner.setEmailAddress("test");
		partner.setPassword("test");
		partner = partnerRepository.save(partner);
		assertNotNull(partner);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void partnerCanBePersistedWithoutLastName() throws Exception{
		Partner partner = new Partner();
		partner.setFirstName("Jack");
		partner.setAirline(new Airline("test"));
		partner.setEmailAddress("test");
		partner.setPassword("test");
		partner = partnerRepository.save(partner);
		assertNotNull(partner);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void partnerCanBePersistedWithoutEmail() throws Exception{
		Partner partner = new Partner();
		partner.setLastName("Captain");
		partner.setAirline(new Airline("test"));
		partner.setFirstName("Jack");
		partner.setPassword("test");
		partner = partnerRepository.save(partner);
		assertNotNull(partner);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void partnerCanBePersistedWithoutAirline() throws Exception{
		Partner partner = new Partner();
		partner.setLastName("Captain");
		partner.setEmailAddress("test");
		partner.setPassword("test");
		partner = partnerRepository.save(partner);
		assertNotNull(partner);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void partnerCanBePersistedWithoutPassword() throws Exception{
		Partner partner = new Partner();
		partner.setLastName("Captain");
		partner.setAirline(new Airline("test"));
		partner.setEmailAddress("test");
		partner.setFirstName("Jack");
		partner = partnerRepository.save(partner);
		assertNotNull(partner);		
	}
	
	@Test
	public void partnerCanBeRemoved() throws Exception{
		Partner partner1 = new Partner("captain","jack","captain@jack.com","blablabla",new Airline("SN Brussels Airlines"));
		Partner partner2 = new Partner("captain","jack","captain@jack.com","blablabla",new Airline("SN Brussels Airlines"));
		partnerRepository.save(partner1);
		partnerRepository.save(partner2);
		partnerRepository.remove(partner2.getId());
		List<Partner> partnerList = partnerRepository.findAll();
		assertEquals(1, partnerList.size());
	}
	
	@Test
	public void partnerCanBeUpdated() throws Exception{
		Partner partner1 = new Partner("captain","jack","captain@jack.com","blablabla",new Airline("SN Brussels Airlines"));
		Partner partner2 = new Partner("captain","jack","captain@jack.com","blablabla",new Airline("SN Brussels Airlines"));
		partnerRepository.save(partner1);
		partnerRepository.save(partner2);
		partner2.setLastName("Jane");
		partner2 = partnerRepository.update(partner2, partner2.getId());
		assertEquals("Jane",partner2.getLastName());
	}

}
