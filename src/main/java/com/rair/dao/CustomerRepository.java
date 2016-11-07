package com.rair.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Customer;

public class CustomerRepository {
	
	@PersistenceContext
    EntityManager em;

    public Customer save(Customer customer) {
        em.persist(customer);
        return customer;
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return em.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    public void remove(long customerId) {
        em.remove(em.getReference(Customer.class, customerId));
    }
    
    public Customer update(Customer customer, Long customerId){
    	Customer oldCustomer = em.find(Customer.class, customerId);
    	oldCustomer = customer;
    	em.merge(oldCustomer);
    	return oldCustomer;
    }


}
