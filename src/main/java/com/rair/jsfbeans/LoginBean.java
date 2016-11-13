package com.rair.jsfbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.rair.dao.PersonReposiroty;
import com.rair.domain.Customer;
import com.rair.domain.Employee;
import com.rair.domain.Partner;
import com.rair.domain.Person;
import com.rair.jsf.converters.PasswordConverter;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

	private String email;
	private String password;
	private Person person;
	private PasswordConverter passwordConverter;

	@Inject
	private PersonReposiroty personReposiroty;

	@ManagedProperty("#{loginServiceBean}")
	private LoginServiceBean loginServiceBean;

	@ManagedProperty("#{bookingServiceBean}")
	private BookingServiceBean bookingServiceBean;
	
	@PostConstruct
	public void init(){
		try{
			passwordConverter = new PasswordConverter();
		}catch (Exception e){
			//nothing
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PersonReposiroty getPersonReposiroty() {
		return personReposiroty;
	}

	public void setPersonReposiroty(PersonReposiroty personReposiroty) {
		this.personReposiroty = personReposiroty;
	}

	public LoginServiceBean getLoginServiceBean() {
		return loginServiceBean;
	}

	public void setLoginServiceBean(LoginServiceBean loginServiceBean) {
		this.loginServiceBean = loginServiceBean;
	}

	public BookingServiceBean getBookingServiceBean() {
		return bookingServiceBean;
	}

	public void setBookingServiceBean(BookingServiceBean bookingServiceBean) {
		this.bookingServiceBean = bookingServiceBean;
	}

	public String doLogin() {
		String encryptedPw;
		if(passwordConverter !=null){
			encryptedPw = passwordConverter.encrypt(password);
		}
		else{
			encryptedPw = password;
		}
		person = personReposiroty.retrievePerson(email, encryptedPw);
		if (person == null) {
			return "loginFailed";
		}
		loginServiceBean.login(person.getEmailAddress());
		if (person instanceof Customer) {
			System.out.println("Customer: " + person.getFirstName());
			bookingServiceBean.setCustomer((Customer) person);
			if (bookingServiceBean.getFlight() == null) {
				return "toIndex";
			} else {
				return "toBooking";
			}
		} else if (person instanceof Employee) {
			System.out.println("Employee: " + person.getFirstName());
			return "toAdmin";
		} else if (person instanceof Partner) {
			System.out.println("Partner: " + person.getFirstName());
			return "toFlightPage";
		}
		return "toIndex";
	}

}
