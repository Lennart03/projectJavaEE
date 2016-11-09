package com.rair.jsfbeans;

import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.rair.domain.Person;

@ManagedBean(name="loginServiceBean")
@ApplicationScoped
public class LoginServiceBean {

	private Map<String, Boolean> loginMap;
	
	
	
	public Map<String, Boolean> getLoginMap() {
		return loginMap;
	}

	public void setLoginMap(Map<String, Boolean> loginMap) {
		this.loginMap = loginMap;
	}

	public void login(String email) {
		loginMap.put(email, true);
	}
	
	public void logout(Person person){
		loginMap.put(person.getEmailAddress(), false);
	}
	
	
}
