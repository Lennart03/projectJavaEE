package com.rair.jsfbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirlineRepository;
import com.rair.dao.PartnerRepository;
import com.rair.domain.Airline;
import com.rair.domain.Partner;

@ManagedBean
@ViewScoped
public class PartnerService {
	
	@Inject
    private PartnerRepository partnerRepository;
	
	@Inject
	private AirlineRepository airlineRepository;
		
	private List<Airline> airlines;
	private List<Partner> partners;
	
	@PostConstruct
    public void init() {
        airlines = airlineRepository.retrieveAllAirlines();
        partners = partnerRepository.findAll();
    }

	public List<Airline> getAirlines() {
		return airlines;
	}
	
	public void onRowEdit(RowEditEvent event) {
		Partner partner = (Partner) event.getObject();
		if(partner.getId()!=null){
			partner = partnerRepository.update(partner, partner.getId());
			FacesMessage msg = new FacesMessage("Partner Edited", partner.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			partner.setPassword("newpassword");
			partner = partnerRepository.save(partner);
			FacesMessage msg = new FacesMessage("Added new partner", partner.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
     
    public void onRowCancel(RowEditEvent event) {
    	Partner partner = (Partner) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", partner.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deletePartner(String email){
    	if(email!=null && email != ""){
    	Partner partner = partnerRepository.findByEmail(email);
    	partnerRepository.remove(partner.getId());
    	FacesMessage msg = new FacesMessage("Partner deleted", partner.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
        init();
    }
	
    public Partner getPartner(int index){
		return partners.get(index);
	}
    
    
    
    public PartnerRepository getPartnerRepository() {
		return partnerRepository;
	}

	public void setPartnerRepository(PartnerRepository partnerRepository) {
		this.partnerRepository = partnerRepository;
	}

	public AirlineRepository getAirlineRepository() {
		return airlineRepository;
	}

	public void setAirlineRepository(AirlineRepository airlineRepository) {
		this.airlineRepository = airlineRepository;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

	public void setAirlines(List<Airline> airlines) {
		this.airlines = airlines;
	}

	public void addAction(){
    	partners.add(new Partner());
    }
	
}
