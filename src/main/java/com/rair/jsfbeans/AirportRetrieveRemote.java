package com.rair.jsfbeans;

import java.util.List;

import javax.ejb.Remote;

import com.rair.domain.Airport;

@Remote
public interface AirportRetrieveRemote {
	
	List<Airport> findAll();

}
