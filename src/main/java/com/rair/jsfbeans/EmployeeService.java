package com.rair.jsfbeans;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.primefaces.event.RowEditEvent;

import com.rair.dao.EmployeeRepository;
import com.rair.domain.Employee;
import com.rair.jsf.converters.PasswordConverter;
import com.rair.mail.MailSender;

@ManagedBean
@ViewScoped
public class EmployeeService {
	
	@Inject
	EmployeeRepository employeeRepository;
	
	private MailSender mailSender = new MailSender();

	public EmployeeRepository getEmployeeRepository() {
		return employeeRepository;
	}

	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	private List<Employee> employees;

	private PasswordConverter passwordConverter;

	@PostConstruct
	public void init() {
		employees = employeeRepository.findAll();
		try {
			passwordConverter = new PasswordConverter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		Employee employee = (Employee) event.getObject();
		if(employee.getId()!=null){
			employee = employeeRepository.update(employee, employee.getId());
			FacesMessage msg = new FacesMessage("Employee Edited", employee.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			String password = getNewRandomPassword();
			System.out.println("Het oorspronkelijke paswoord is: " + password);
			employee.setPassword(passwordConverter.encrypt(password));
			System.out.println("Het encrypted password dat naar de database gaat is: " + employee.getPassword());
			employee = employeeRepository.save(employee);
			FacesMessage msg = new FacesMessage("Added new employee", employee.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("Het decoded password is: " + passwordConverter.decrypt(employee.getPassword()));
			mailSender.setTextMessage("employeeRegistration",null);
			try {
				mailSender.sendMail(employee.getEmailAddress());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
     
    public void onRowCancel(RowEditEvent event) {
    	Employee employee = (Employee) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", employee.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deleteEmployee(String email){
    	if(email!=null && email != ""){
    	Employee employee = employeeRepository.findByEmail(email);
    	employeeRepository.remove(employee.getId());
    	FacesMessage msg = new FacesMessage("Employee deleted", employee.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
        init();
    }
	
    public Employee getEmployee(int index){
		return employees.get(index);
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void addAction(){
    	employees.add(new Employee());
    }
	
	public String getNewRandomPassword(){
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(8);
		Random random = new Random();
		for(int i = 1 ; i<9 ; i++){
			int index = random.nextInt(characters.length());
		    sb.append(characters.charAt(index));
		}
		return sb.toString();
	}

}
