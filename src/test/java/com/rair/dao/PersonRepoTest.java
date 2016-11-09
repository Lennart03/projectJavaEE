package com.rair.dao;

import org.junit.Before;
import org.junit.Test;

import com.rair.domain.Customer;
import com.rair.domain.Employee;
import com.rair.domain.Partner;
import com.rair.domain.Person;
import com.rair.testcore.JpaPersistenceTest;

public class PersonRepoTest extends JpaPersistenceTest{
	
	private PersonReposiroty personReposiroty;
	
	@Before
	public void init() {
		personReposiroty = new PersonReposiroty();
		personReposiroty.entityManager = entityManager();
	}
	
	@Test
	public void retrievPersonTest() {
		Person person = personReposiroty.retrievePerson("customer1@rair.com", "passLennart");
		System.out.println("First name: "+person.getFirstName() +"\tLast name: "+ person.getLastName());
		System.out.println("Is the retrieved person a customer: " + (person instanceof Customer));
		System.out.println("Is the retrieved person a employee: "+ (person instanceof Employee));
		System.out.println("Is the retrieved person a partner: "+ (person instanceof Partner));
		person = personReposiroty.retrievePerson("employee1@rair.com", "passEmpl");
		System.out.println("First name: "+person.getFirstName() +"\tLast name: "+ person.getLastName());
		System.out.println("Is the retrieved person a customer: " + (person instanceof Customer));
		System.out.println("Is the retrieved person a employee: "+ (person instanceof Employee));
		System.out.println("Is the retrieved person a partner: "+ (person instanceof Partner));
		person = personReposiroty.retrievePerson("partner1@rair.com", "passPartner");
		System.out.println("First name: "+person.getFirstName() +"\tLast name: "+ person.getLastName());
		System.out.println("Is the retrieved person a customer: " + (person instanceof Customer));
		System.out.println("Is the retrieved person a employee: "+ (person instanceof Employee));
		System.out.println("Is the retrieved person a partner: "+ (person instanceof Partner));
	}

}
