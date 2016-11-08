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

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirportRepository;
import com.rair.domain.Airport;
import com.rair.domain.Region;

@ManagedBean
@SessionScoped
public class AirportRetrieveBean implements Serializable{
	
	@Inject
	AirportRepository airportRepo;
		
	private List<Airport> airports = new ArrayList<Airport>();

	public List<Airport> getAirports() {
		System.out.println("functie aangeroepen");
		this.airports = airportRepo.findAll();
		return airports;
	}
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Airport Edited", ((Airport) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Airport) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<Region> getRegions(){
    	List<Region> regionList = Arrays.asList(Region.values());
    	return regionList;
    }
	

}
