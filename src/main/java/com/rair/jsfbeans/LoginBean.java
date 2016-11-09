package com.rair.jsfbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.rair.dao.PersonReposiroty;
import com.rair.domain.Customer;
import com.rair.domain.Employee;
import com.rair.domain.Partner;
import com.rair.domain.Person;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

	private String email;
	private String password;
	private Person person;

	@Inject
	private PersonReposiroty personReposiroty;

	@ManagedProperty("#{loginServiceBean}")
	private LoginServiceBean loginServiceBean;

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

	public String doLogin() {
		person = personReposiroty.retrievePerson(email, password);
		if (person == null) {
			return "loginFailed";
		}
		loginServiceBean.login(person.getEmailAddress());
		if (person instanceof Customer) {
			System.out.println("Customer: " + person.getFirstName());
			return "toIndex";
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
