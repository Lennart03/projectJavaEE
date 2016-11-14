package com.rair.jsfbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "loginServiceBean")
@ApplicationScoped
public class LoginServiceBean implements Serializable {
	

	private static final long serialVersionUID = -4005917786611179182L;
	private Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	private TimeZone timeZone;
	private Map<String, Boolean> loginMap;
	private String localeString;

	@PostConstruct
	public void init() {
		loginMap = new HashMap<>();
	}

	
	
	public String getLocaleString() {
		return localeString;
	}



	public void setLocaleString(String localeString) {
		this.localeString = localeString;
		setLocale(new Locale(localeString));
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
