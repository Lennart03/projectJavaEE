package com.rair.dao;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;

import com.rair.domain.Customer;
import com.rair.testcore.JpaPersistenceTest;

public class CustomerRepoTest extends JpaPersistenceTest{
	
CustomerRepository customerRepository;
	
	
	@Before
    public void initializeRepository() {
		customerRepository = new CustomerRepository();
		customerRepository.em = entityManager();
    }
	
	@Test
	public void customerCanBePersisted() throws Exception{
		Customer customer = new Customer("captain","jack","captain@jack.com","blablabla");
		customer = customerRepository.save(customer);
		assertNotNull(customer);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void customerCanBePersistedWithoutFirstName() throws Exception{
		Customer customer = new Customer();
		customer.setLastName("Captain");
		customer.setEmailAddress("test");
		customer.setPassword("test");
		customer = customerRepository.save(customer);
		assertNotNull(customer);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void customerCanBePersistedWithoutLastName() throws Exception{
		Customer customer = new Customer();
		customer.setFirstName("Jack");
		customer.setEmailAddress("test");
		customer.setPassword("test");
		customer = customerRepository.save(customer);
		assertNotNull(customer);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void customerCanBePersistedWithoutEmail() throws Exception{
		Customer customer = new Customer();
		customer.setFirstName("Jack");
		customer.setLastName("test");
		customer.setPassword("test");
		customer = customerRepository.save(customer);
		assertNotNull(customer);		
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void customerCanBePersistedWithoutPassword() throws Exception{
		Customer customer = new Customer();
		customer.setFirstName("Jack");
		customer.setEmailAddress("test");
		customer.setLastName("test");
		customer = customerRepository.save(customer);
		assertNotNull(customer);		
	}
	
	@Test
	public void customerCanBeRemoved() throws Exception{
		Customer customer1 = new Customer("captain","jack","captain@jack.com","blablabla");
		Customer customer2 = new Customer("captain","jack","captain@jack.com","blablabla");
		customerRepository.save(customer1);
		customerRepository.save(customer2);
		customerRepository.remove(customer2.getId());
		List<Customer> partnerList = customerRepository.findAll();
		assertEquals(1, partnerList.size());
	}
	
	@Test
	public void customerCanBeUpdated() throws Exception{
		Customer customer1 = new Customer("captain","jack","captain@jack.com","blablabla");
		Customer customer2 = new Customer("captain","jack","captain@jack.com","blablabla");
		customerRepository.save(customer1);
		customerRepository.save(customer2);
		customer2.setLastName("Jane");
		customer2 = customerRepository.update(customer2, customer2.getId());
		assertEquals("Jane",customer2.getLastName());
	}

}
