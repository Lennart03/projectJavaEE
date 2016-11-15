package com.rair.jsfbeans;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
	private String firstName;
	private String password;
	private Person person;
	private boolean loggedIn;
	private PasswordConverter passwordConverter;
	private Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	private TimeZone timeZone;
	private String localeString;

	@Inject
	private PersonReposiroty personReposiroty;

	@ManagedProperty("#{loginServiceBean}")
	private LoginServiceBean loginServiceBean;

	@ManagedProperty("#{bookingServiceBean}")
	private BookingServiceBean bookingServiceBean;

	@PostConstruct
	public void init() {
		loggedIn = false;
		try {
			passwordConverter = new PasswordConverter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public PasswordConverter getPasswordConverter() {
		return passwordConverter;
	}

	public void setPasswordConverter(PasswordConverter passwordConverter) {
		this.passwordConverter = passwordConverter;
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
		loggedIn = false;
		String encryptedPw;
		if (passwordConverter != null) {
			encryptedPw = passwordConverter.encrypt(password);
		} else {
			encryptedPw = password;
		}
		person = personReposiroty.retrievePerson(email, encryptedPw);
		if (person == null) {
			return "toIndex";
		}
		loginServiceBean.login(person.getEmailAddress());
		loggedIn = true;
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

	public void login(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		person = personReposiroty.retrievePerson(email, passwordConverter.encrypt(password));
		loggedIn = false;

		if (person != null) {
			loggedIn = true;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome",
					person.getFirstName() + " " + person.getLastName());
			loginServiceBean.login(email);
		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login error", "Invalid credentials");
		}

		FacesContext.getCurrentInstance().addMessage(null, message);
		context.addCallbackParam("loggedIn", loggedIn);
	}

	public String logout() {
		System.out.println("Loggin out.");
		loginServiceBean.logout(person.getEmailAddress());
		person = null;
		email = "";
		firstName = "";
		password = "";
		loggedIn = false;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout", "You have been logged out."));
		return "index.xhtml?faces-redirect=true";
	}

	public void openLoginDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("login", options, null);
	}

	public void closeLoginDialog() {
		RequestContext.getCurrentInstance().closeDialog(Arrays.asList(firstName, person.getLastName()));
	}

	public void onReturnFromLogin(SelectEvent event) {
		RequestContext.getCurrentInstance().closeDialog(event.getObject());
	}

	public Long getAirlineId() {
		if (person != null) {
			if (person instanceof Partner) {
				Partner partner = (Partner) person;
				if (partner.getAirline() != null) {
					return partner.getAirline().getId();
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getLocaleString() {
		return localeString;
	}

	public void setLocaleString(String localeString) {
		this.localeString = localeString;
		setLocale(new Locale(localeString));
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	public void change() {
		System.out.println(localeString);
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(locale.toString());
	}

}
