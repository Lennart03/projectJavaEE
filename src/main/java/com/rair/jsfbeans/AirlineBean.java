package com.rair.jsfbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.rair.dao.AirlineRepository;

@ManagedBean
@ViewScoped
public class AirlineBean {

	private String airlineName;
	
	@Inject
	private AirlineRepository airlineRepository;
	
	public String createAirline() {
		airlineRepository.createAirline(airlineName);
		return "succes.xhtml";
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	
	
	
}
