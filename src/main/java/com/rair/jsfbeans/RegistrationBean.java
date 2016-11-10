package com.rair.jsfbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.rair.dao.CustomerRepository;
import com.rair.domain.Customer;

@ManagedBean(name = "registrationBean")
@ViewScoped
public class RegistrationBean implements Serializable {

	private static final long serialVersionUID = 2979273877443227480L;

	@Inject
	private CustomerRepository customerRepository;

	@ManagedProperty("#{loginServiceBean}")
	private LoginServiceBean loginServiceBean;

	private String firstName;
	private String lastName;
	private String emailAddress;
	private String passWord;

	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public LoginServiceBean getLoginServiceBean() {
		return loginServiceBean;
	}

	public void setLoginServiceBean(LoginServiceBean loginServiceBean) {
		this.loginServiceBean = loginServiceBean;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String register() {
		Customer customer = new Customer(firstName, lastName, emailAddress, passWord);
		customerRepository.save(customer);
		loginServiceBean.login(emailAddress);
		return "toBooking";
	}

}
