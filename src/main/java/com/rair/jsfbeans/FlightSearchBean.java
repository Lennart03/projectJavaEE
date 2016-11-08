package com.rair.jsfbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;

import com.rair.domain.Airport;

@ManagedBean
@ViewScoped
public class FlightSearchBean implements Serializable{
	

	private static final long serialVersionUID = 8792693292832076782L;

	@ManagedProperty("#{airportServiceBean}")
	private AirportServiceBean airportServiceBean;

	private Airport arrivalAirport;
	
	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		System.out.println("Arrival Airport set: " +arrivalAirport);
		this.arrivalAirport = arrivalAirport;
	}
	
	public List<Airport> completeAirports(String query) {
		List<Airport> filteredAirports = new ArrayList<>();
		for(Airport airport : airportServiceBean.getAirports()) {
			if(airport.getCountry().contains(query) || airport.getCity().contains(query)){
				filteredAirports.add(airport);
			}
		}
		return filteredAirports;
	}

	public AirportServiceBean getAirportServiceBean() {
		return airportServiceBean;
	}

	public void setAirportServiceBean(AirportServiceBean airportServiceBean) {
		this.airportServiceBean = airportServiceBean;
	}
	
	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

}
