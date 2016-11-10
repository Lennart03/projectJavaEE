package com.rair.jsfbeans;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginServiceBean")
@ApplicationScoped
public class LoginServiceBean {
	
	private Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	private TimeZone timeZone;
	private Map<String, Boolean> loginMap;

	@PostConstruct
	public void init() {
		loginMap = new HashMap<>();
	}

	public Map<String, Boolean> getLoginMap() {
		return loginMap;
	}

	public void setLoginMap(Map<String, Boolean> loginMap) {
		this.loginMap = loginMap;
	}
	
	
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Boolean isLoggedIn(String email){
		if(loginMap.containsKey(email)){
			return loginMap.get(email);
		}
		return false;
	}

	public void login(String email) {
		System.out.println("Loggin in: " + email);
		loginMap.put(email, true);
	}

	public String logout(String email) {
		System.out.println("Logout: " + email);
		loginMap.put(email, false);
		return "toIndex";
	}
	
	public TimeZone getTimeZone() {
	    return TimeZone.getDefault();
	}

}
