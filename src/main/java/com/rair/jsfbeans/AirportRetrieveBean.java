package com.rair.jsfbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirportRepository;
import com.rair.domain.Airport;
import com.rair.domain.Region;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AirportRetrieveBean implements Serializable{
	
	@Inject
	AirportRepository airportRepo;
		
	private List<Airport> airports = new ArrayList<Airport>();

	public List<Airport> getAirports() {
		this.airports = airportRepo.findAll();
		return airports;
	}
	
	public void onRowEdit(RowEditEvent event) {
		Airport airport = (Airport) event.getObject();
        FacesMessage msg = new FacesMessage("Airport Edited", airport.getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("Nieuwe id frontend: " + airport.getId());
        airport = airportRepo.update(airport, airport.getId());
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
    
}
