package com.rair.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Partner;

@Stateless
public class PartnerRepository {
	
	@PersistenceContext
    EntityManager em;

    public Partner save(Partner partner) {
        em.persist(partner);
        return partner;
    }

    public Partner findById(Long id) {
        return em.find(Partner.class, id);
    }
    
    public Partner findByEmail(String email){
    	Partner partner = em.createQuery("selet p from Partner p where p.email = " + email,Partner.class).getSingleResult();
    	return partner;
    }

    public List<Partner> findAll() {
        return em.createQuery("select p from Partner p", Partner.class).getResultList();
    }

    public void remove(long partnerId) {
        em.remove(em.getReference(Partner.class, partnerId));
    }
    
    public Partner update(Partner partner, Long partnerId){
    	Partner oldPartner = em.find(Partner.class, partnerId);
    	System.out.println("Oude partner naam: " + oldPartner.getFirstName());
    	System.out.println("Nieuwe partner naam: " + oldPartner.getFirstName());
    	oldPartner = partner;
    	em.merge(oldPartner);
    	return oldPartner;
    }

}
