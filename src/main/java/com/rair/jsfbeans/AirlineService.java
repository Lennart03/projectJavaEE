package com.rair.jsfbeans;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirlineRepository;
import com.rair.domain.Airline;
import com.rair.domain.Region;

@ManagedBean
@ViewScoped
public class AirlineService {

	@Inject
    private AirlineRepository airlineRepository;
		
	private List<Airline> airlines;
	
	@PostConstruct
    public void init() {
        airlines = airlineRepository.retrieveAllAirlines();
    }

	public List<Airline> getAirlines() {
		System.out.println("nieuwe lijst opgevraagd");
		return airlines;
	}
	
	public void onRowEdit(RowEditEvent event) {
		Airline airline = (Airline) event.getObject();
		if(airline.getId()!=null){
			airline = airlineRepository.update(airline, airline.getId());
			FacesMessage msg = new FacesMessage("Airline Edited", airline.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			airline = airlineRepository.createAirline(airline.getName());
			FacesMessage msg = new FacesMessage("Added new airline", airline.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
     
    public void onRowCancel(RowEditEvent event) {
    	Airline airline = (Airline) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", airline.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deleteAirline(String airlineName){
    	if(airlineName!=null && airlineName != ""){
    	Airline airline = airlineRepository.retrieveAirline(airlineName);
    	airlineRepository.deleteAirline(airline.getId());
    	FacesMessage msg = new FacesMessage("Airline deleted", airline.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
        init();
    }
    
    public List<Region> getRegions(){
    	List<Region> regionList = Arrays.asList(Region.values());
    	return regionList;
    }
	
    public Airline getAirline(int index){
		return airlines.get(index);
	}

	public AirlineRepository getAirlineRepository() {
		return airlineRepository;
	}

	public void setAirlineRepository(AirlineRepository airlineRepository) {
		this.airlineRepository = airlineRepository;
	}
    
    public void addAction(){
    	airlines.add(new Airline());
    }
	
	
	
}
