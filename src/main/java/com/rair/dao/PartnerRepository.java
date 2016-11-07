package com.rair.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rair.domain.Partner;

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

    public List<Partner> findAll() {
        return em.createQuery("select p from Partner p", Partner.class).getResultList();
    }

    public void remove(long partnerId) {
        em.remove(em.getReference(Partner.class, partnerId));
    }
    
    public Partner update(Partner partner, Long partnerId){
    	Partner oldPartner = em.find(Partner.class, partnerId);
    	oldPartner = partner;
    	em.merge(oldPartner);
    	return oldPartner;
    }

}
