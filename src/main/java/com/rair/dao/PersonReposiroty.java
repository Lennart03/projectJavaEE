package com.rair.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.rair.domain.Person;

@Stateless
public class PersonReposiroty {

	@PersistenceContext
	EntityManager entityManager;
	
	public Person retrievePerson(String email, String password) {
		
		Person person;
		try {
			person = entityManager.createQuery("select p from Person p where p.emailAddress = '"+email+"' and p.password = '" +password+"'", Person.class).getSingleResult();
		} catch (NoResultException e) {
			person = null;
		}
		return person;
	}
	
	
}
