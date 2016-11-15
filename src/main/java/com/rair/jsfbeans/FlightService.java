package com.rair.jsfbeans;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.RowEditEvent;

import com.rair.dao.AirlineRepository;
import com.rair.dao.AirportRepository;
import com.rair.dao.BookingRepository;
import com.rair.dao.FlightRepository;
import com.rair.domain.Airline;
import com.rair.domain.Airport;
import com.rair.domain.Booking;
import com.rair.domain.Flight;
import com.rair.domain.TravelingClass;

@ManagedBean
@ViewScoped
public class FlightService {

	@Inject
	private FlightRepository flightRepository;
	
	@Inject
	private AirportRepository airportRepository;
	
	@Inject
	private AirlineRepository airlineRepository;
	
	@Inject
	private BookingRepository bookingRepository;
	
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@ManagedProperty("#{airportServiceBean}")
	private AirportServiceBean airportServiceBean;
	
	private List<Flight> futureFlightsByAirline;
	private List<Airport> airports;
	
	private int econValue;
	private int businessValue;
	private int firstClassValue;


	@PostConstruct
	public void init(){
		airports = airportRepository.findAll();
		Long id = loginBean.getAirlineId();
		if (id != null){
			futureFlightsByAirline = flightRepository.retrieveFutureFlightsByAirline(id);
		}else{
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			try {
				ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

	public LoginBean getLoginBean() {
		return loginBean;
	}



	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}



	public FlightRepository getFlightRepository() {
		return flightRepository;
	}

	public void setFlightRepository(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getFutureFlightsByAirline() {
		return futureFlightsByAirline;
	}

	public AirportRepository getAirportRepository() {
		return airportRepository;
	}

	public void setAirportRepository(AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}

	public List<Airport> getAirports() {
		return airports;
	}

	public void setAirports(List<Airport> airports) {
		this.airports = airports;
	}

	public void onRowEdit(RowEditEvent event) {
		System.out.println(event.getObject());
		Flight flight = (Flight) event.getObject();
		if(checkNumberOfSoldSeatsForTravelingClass("ECONOMY", flight.getId())<flight.getEconomySeats()){
			flight.addNumberOfSeatsForClass(TravelingClass.ECONOMY, new Integer(flight.getEconomySeats()));
		}else{
			flight.addNumberOfSeatsForClass(TravelingClass.ECONOMY, checkNumberOfSoldSeatsForTravelingClass("ECONOMY", flight.getId()));
		}
		if(checkNumberOfSoldSeatsForTravelingClass("BUSINESS", flight.getId())<flight.getBusinessSeats()){
			flight.addNumberOfSeatsForClass(TravelingClass.BUSINESS, new Integer(flight.getBusinessSeats()));
		}else{
			flight.addNumberOfSeatsForClass(TravelingClass.BUSINESS, checkNumberOfSoldSeatsForTravelingClass("BUSINESS", flight.getId()));
		}
		if(checkNumberOfSoldSeatsForTravelingClass("FIRST_CLASS", flight.getId())<flight.getFirstClassSeats()){
			flight.addNumberOfSeatsForClass(TravelingClass.FIRST_CLASS, new Integer(flight.getFirstClassSeats()));
		}else{
			flight.addNumberOfSeatsForClass(TravelingClass.FIRST_CLASS, checkNumberOfSoldSeatsForTravelingClass("FIRST_CLASS", flight.getId()));
		}
		if(flight.getId()!= null){
			flight = flightRepository.update(flight, flight.getId());
			FacesMessage msg = new FacesMessage("Flight Edited", flight.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			Airline airline = airlineRepository.retrieveAirline("Air Botswana");
			System.out.println("Airline ID = " + airline.getId());
			flight.setAirline(airlineRepository.retrieveAirline("Air Botswana"));
			flightRepository.createFlight(flight);
			FacesMessage msg = new FacesMessage("Added new flight", flight.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
    }
     
    public void onRowCancel(RowEditEvent event) {
    	Flight flight = (Flight) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", flight.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deleteFlight(String flightNumber){
    	if(flightNumber!=null && flightNumber != ""){
    	Flight flight = flightRepository.retrieveFlightByFlightNumber(flightNumber);
    	flightRepository.remove(flight.getId());
    	FacesMessage msg = new FacesMessage("Flight deleted", flight.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
        init();
    }
    
    public void addAction(){
    	futureFlightsByAirline.add(new Flight());
    }

	public AirportServiceBean getAirportServiceBean() {
		return airportServiceBean;
	}

	public void setAirportServiceBean(AirportServiceBean airportServiceBean) {
		this.airportServiceBean = airportServiceBean;
	}

	public Integer checkNumberOfSoldSeatsForTravelingClass(String stringClass, Long flightID) {
		List<Booking> bookings = bookingRepository.retrieveBookingFromFlightByTravelingClass(stringClass,flightID);
		Integer number = 0;
		for(Booking b:bookings){
			number = number + b.getNumberOfSeats();
		}
		return number;
	}

	public int getEconValue() {
		return econValue;
	}

	public void setEconValue(int econValue) {
		this.econValue = econValue;
	}

	public int getBusinessValue() {
		return businessValue;
	}

	public void setBusinessValue(int businessValue) {
		this.businessValue = businessValue;
	}

	public int getFirstClassValue() {
		return firstClassValue;
	}

	public void setFirstClassValue(int firstClassValue) {
		this.firstClassValue = firstClassValue;
	}
	
	public void initValues(RowEditEvent event){
		Flight flight = (Flight) event.getObject();
		econValue = checkNumberOfSoldSeatsForTravelingClass("ECONOMY",flight.getId());
		businessValue = checkNumberOfSoldSeatsForTravelingClass("BUSINESS",flight.getId());
		firstClassValue = checkNumberOfSoldSeatsForTravelingClass("FIRST_CLASS",flight.getId());
	}
	
}
