package com.rair.jsfbeans;

import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.rair.domain.Customer;
import com.rair.domain.Employee;
import com.rair.domain.Partner;

@ManagedBean(name="loginBean")
@ApplicationScoped
public class LoginServiceBean {

	private Map<Customer, Boolean> customerLoginMap;
	private Map<Employee, Boolean> employeeLoginMap;
	private Map<Partner, Boolean> partnerLoginMap;
	
	
	
}
