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
	
	public String doLogin(){
		Person person = personReposiroty.retrievePerson(email, password);
		if(person == null) {
			return "toRegister";
		} else if (person instanceof Customer){
			Customer customer = (Customer) person;
			return "toIndex";
		} else if (person instanceof Employee){
		} else if (person instanceof Partner) {
			
		}
		return "toIndex";
	}

}
