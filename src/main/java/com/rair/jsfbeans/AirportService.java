package com.rair.jsfbeans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirportRepository;
import com.rair.domain.Airport;
import com.rair.domain.Region;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AirportService implements Serializable{
	
	@Inject
    private AirportRepository airportRepository;
		
	private List<Airport> airports;
	
	@PostConstruct
    public void init() {
        airports = airportRepository.findAll();
    }

	public List<Airport> getAirports() {
		return airports;
	}
	
	public void onRowEdit(RowEditEvent event) {
		Airport airport = (Airport) event.getObject();
		if(airport.getId()!=null){
			airport = airportRepository.update(airport, airport.getId());
			FacesMessage msg = new FacesMessage("Airport Edited", airport.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			airport = airportRepository.save(airport);
			FacesMessage msg = new FacesMessage("Added new airport", airport.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
     
    public void onRowCancel(RowEditEvent event) {
    	Airport airport = (Airport) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", airport.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<Region> getRegions(){
    	List<Region> regionList = Arrays.asList(Region.values());
    	return regionList;
    }
	
    public Airport getAirport(int index){
		return airports.get(index);
	}

	public AirportRepository getAirportRepository() {
		return airportRepository;
	}

	public void setAirportRepository(AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}
    
    public void addAction(){
    	airports.add(new Airport());
    }
    
}
