package com.rair.jsfbeans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.rair.dao.FlightRepository;
import com.rair.domain.Airport;
import com.rair.domain.Flight;
import com.rair.domain.TravelingClass;

@ManagedBean
@ViewScoped
public class FlightSearchBean implements Serializable {

	private static final long serialVersionUID = 8792693292832076782L;

	@ManagedProperty("#{airportServiceBean}")
	private AirportServiceBean airportServiceBean;

	@ManagedProperty("#{bookingServiceBean}")
	private BookingServiceBean bookingServiceBean;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	@Inject
	private FlightRepository flightRepository;

	private Airport arrivalAirport;
	private Airport departureAirport;
	private List<Flight> singleFlights = new ArrayList<>();
	private List<Flight> returnFlights = new ArrayList<>();
	private Date departureDate;
	private Date departureDateReturnFlight;
	private String mindate;
	private String mindateReturn;
	private boolean singleFlight = true;
	private double priceOfTicket;
	private double priceOfReturnTicket;

	private String customerID;

	@PostConstruct
	public void init() {
		departureAirport = airportServiceBean.getAirports().get(0);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		departureDate = cal.getTime();
		departureDateReturnFlight = cal.getTime();
		mindate = dateFormat.format(cal.getTime());
		mindateReturn = dateFormat.format(cal.getTime());
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getDepartureDateReturnFlight() {
		return departureDateReturnFlight;
	}

	public void setDepartureDateReturnFlight(Date departureDateReturnFlight) {
		this.departureDateReturnFlight = departureDateReturnFlight;
	}

	public double getPriceOfTicket() {
		return priceOfTicket;
	}

	public void setPriceOfTicket(double priceOfTicket) {
		this.priceOfTicket = priceOfTicket;
	}

	public Airport getArrivalAirport() {
		System.out.println("Arrival airport: " + this.arrivalAirport);
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	public AirportServiceBean getAirportServiceBean() {
		return airportServiceBean;
	}

	public boolean isSingleFlight() {
		return singleFlight;
	}

	public String getMindate() {
		return mindate;
	}

	public void setMindate(String mindate) {
		this.mindate = mindate;
	}

	public String getMindateReturn() {
		return mindateReturn;
	}

	public void setMindateReturn(String mindateReturn) {
		this.mindateReturn = mindateReturn;
	}

	public void setSingleFlight(boolean singleFlight) {
		System.out.println("The flight is a single flight: " + singleFlight);
		this.singleFlight = singleFlight;
	}

	public void setAirportServiceBean(AirportServiceBean airportServiceBean) {
		this.airportServiceBean = airportServiceBean;
	}

	public BookingServiceBean getBookingServiceBean() {
		return bookingServiceBean;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void setBookingServiceBean(BookingServiceBean bookingServiceBean) {
		this.bookingServiceBean = bookingServiceBean;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public FlightRepository getFlightRepository() {
		return flightRepository;
	}

	public void setFlightRepository(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getSingleFlights() {
		return singleFlights;
	}

	public void setSingleFlights(List<Flight> singleFlights) {
		this.singleFlights = singleFlights;
	}

	public List<Flight> getReturnFlights() {
		return returnFlights;
	}

	public void setReturnFlights(List<Flight> returnFlights) {
		this.returnFlights = returnFlights;
	}

	public double getPriceOfReturnTicket() {
		return priceOfReturnTicket;
	}

	public void setPriceOfReturnTicket(double priceOfReturnTicket) {
		this.priceOfReturnTicket = priceOfReturnTicket;
	}

	public List<Airport> completeAirports(String query) {
		System.out.println("Query for aiports: " + query);
		List<Airport> filteredAirports = new ArrayList<>();
		for (Airport airport : airportServiceBean.getAirports()) {
			if (airport.getCountry().toLowerCase().contains(query.toLowerCase())
					|| airport.getCity().toLowerCase().contains(query.toLowerCase())) {
				filteredAirports.add(airport);
			}
		}
		return filteredAirports;
	}

	public String onFlowProcess(FlowEvent event) {
		System.out.println("Old Step: " + event.getOldStep());
		if (event.getNewStep().equals("flights")) {
			if (event.getOldStep().equals("ticketChoice")) {
				bookingServiceBean.setFlight(null);
				bookingServiceBean.setnSeatsWanted(0);
				bookingServiceBean.setPriceOfBooking(0);
				bookingServiceBean.setSelectedTravelClass(TravelingClass.ECONOMY);
			}
			System.out.println("Departure airport: " +departureAirport);
			System.out.println("Arrival airport: " + arrivalAirport);
			singleFlights = flightRepository.retrieveFlightsFromAndTo(departureAirport, arrivalAirport);
			System.out.println(singleFlights);
			if (!singleFlight) {
				returnFlights = flightRepository.retrieveFlightsFromAndTo(arrivalAirport, departureAirport);
			}
		}
		return event.getNewStep();
	}

	public void onRowSelectOutboud(SelectEvent event) {
		Flight flight = (Flight) event.getObject();
		bookingServiceBean.setFlight(flight);
		priceOfTicket = bookingServiceBean.getFlight().getTicketPriceEconomyClass();
		bookingServiceBean.setSelectedTravelClass(TravelingClass.ECONOMY);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Outbound flight selected",
				flight.getFlightNumber());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselectOutbound(UnselectEvent event) {
		bookingServiceBean.setFlight(null);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Outbound flight unselected",
				((Flight) event.getObject()).getFlightNumber());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowSelectReturn(SelectEvent event) {
		Flight returnFlight = (Flight) event.getObject();
		bookingServiceBean.setReturnFlight(returnFlight);
		priceOfTicket = bookingServiceBean.getReturnFlight().getTicketPriceEconomyClass();
		bookingServiceBean.setSelectedReturnTravelClass(TravelingClass.ECONOMY);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Return flight selected",
				returnFlight.getFlightNumber());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselectReturn(UnselectEvent event) {
		bookingServiceBean.setReturnFlight(null);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Return flight unselected",
				((Flight) event.getObject()).getFlightNumber());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String bookFlight() {

		System.out.println(loginBean.getPerson());
		if (loginBean.getPerson() == null) {
			return "toLogin";
		}

		return "toLogin";
	}

	public void changePriceOutboundFlight() {
		switch (bookingServiceBean.getSelectedTravelClass()) {
		case BUSINESS:
			priceOfTicket = bookingServiceBean.getFlight().getTicketPriceBusinessClass();
			break;
		case FIRST_CLASS:
			priceOfTicket = bookingServiceBean.getFlight().getTicketPriceFirstClass();
			break;
		default:
			priceOfTicket = bookingServiceBean.getFlight().getTicketPriceEconomyClass();
			break;
		}
		bookingServiceBean.calculatePriceOfBooking(priceOfTicket);
	}

	public void changePriceReturnFlight() {
		switch (bookingServiceBean.getSelectedReturnTravelClass()) {
		case BUSINESS:
			priceOfReturnTicket = bookingServiceBean.getReturnFlight().getTicketPriceBusinessClass();
			break;
		case FIRST_CLASS:
			priceOfReturnTicket = bookingServiceBean.getReturnFlight().getTicketPriceFirstClass();
			break;
		default:
			priceOfReturnTicket = bookingServiceBean.getReturnFlight().getTicketPriceEconomyClass();
			break;
		}
		bookingServiceBean.calculatePriceOfReturnBooking(priceOfReturnTicket);
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public void openRegisteredQuestion() {
		System.out.println("Hello");
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("registerQuestion", options, null);
	}

	public void onReturnFromRegisterQuestion(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Data Returned", event.getObject().toString()));
	}

}
