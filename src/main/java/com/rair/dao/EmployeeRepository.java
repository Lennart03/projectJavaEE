package com.rair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Employee;

@Stateless
public class EmployeeRepository {

	@PersistenceContext
    EntityManager em;

    public Employee save(Employee employee) {
        em.persist(employee);
        return employee;
    }

    public Employee findById(Long id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> findAll() {
        return em.createQuery("select c from Employee c", Employee.class).getResultList();
    }

    public void remove(long employeeId) {
        em.remove(em.getReference(Employee.class, employeeId));
    }
    
    public Employee update(Employee employee, Long employeeId){
    	Employee oldEmployee = em.find(Employee.class, employeeId);
    	oldEmployee = employee;
    	em.merge(oldEmployee);
    	return oldEmployee;
    }

    public Employee findByEmail(String email){
    	Employee employee = em.createQuery("select c from Employee c where c.emailAddress = '" + email + "'",Employee.class).getSingleResult();
    	return employee;
    }
	
}
