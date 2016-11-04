package com.realdolmen.acaddemict;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class TestBean {
	
	private String strings = "Test string 1";

	public TestBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStrings() {
		return strings;
	}

	public void setStrings(String strings) {
		this.strings = strings;
	}
	
	
	
}
