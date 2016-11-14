package com.rair.jsfbeans;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirlineRepository;
import com.rair.dao.PartnerRepository;
import com.rair.domain.Airline;
import com.rair.domain.Partner;
import com.rair.jsf.converters.PasswordConverter;
import com.rair.mail.MailSender;

@ManagedBean
@ViewScoped
public class PartnerService {
	
	@Inject
    private PartnerRepository partnerRepository;
	
	@Inject
	private AirlineRepository airlineRepository;
	
	private MailSender mailSender = new MailSender();
		
	private List<Airline> airlines;
	private List<Partner> partners;
	
	private PasswordConverter passwordConverter;
	
	@PostConstruct
    public void init() {
        airlines = airlineRepository.retrieveAllAirlines();
        partners = partnerRepository.findAll();
        try {
			passwordConverter = new PasswordConverter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			String password = getNewRandomPassword();
			System.out.println("Het oorspronkelijke paswoord is: " + password);
			partner.setPassword(passwordConverter.encrypt(password));
			System.out.println("Het encrypted password dat naar de database gaat is: " + partner.getPassword());
			partner = partnerRepository.save(partner);
			FacesMessage msg = new FacesMessage("Added new partner", partner.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("Het decoded password is: " + passwordConverter.decrypt(partner.getPassword()));
			mailSender.setTextMessage("partnerRegistration");
			try {
				mailSender.sendMail(partner.getEmailAddress());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public String getNewRandomPassword(){
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(8);
		Random random = new Random();
		for(int i = 1 ; i<9 ; i++){
			int index = random.nextInt(characters.length());
		    sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
	
}
