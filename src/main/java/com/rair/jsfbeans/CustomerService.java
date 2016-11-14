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

import com.rair.dao.CustomerRepository;
import com.rair.domain.Customer;
import com.rair.jsf.converters.PasswordConverter;
import com.rair.mail.MailSender;

@ManagedBean
@ViewScoped
public class CustomerService {

	@Inject
	CustomerRepository customerRepository;
	
	private MailSender mailSender = new MailSender();

	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	private List<Customer> customers;

	private PasswordConverter passwordConverter;

	@PostConstruct
	public void init() {
		customers = customerRepository.findAll();
		try {
			passwordConverter = new PasswordConverter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onRowEdit(RowEditEvent event) {
		Customer customer = (Customer) event.getObject();
		if(customer.getId()!=null){
			customer = customerRepository.update(customer, customer.getId());
			FacesMessage msg = new FacesMessage("Customer Edited", customer.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
			String password = getNewRandomPassword();
			System.out.println("Het oorspronkelijke paswoord is: " + password);
			customer.setPassword(passwordConverter.encrypt(password));
			System.out.println("Het encrypted password dat naar de database gaat is: " + customer.getPassword());
			customer = customerRepository.save(customer);
			FacesMessage msg = new FacesMessage("Added new customer", customer.getId().toString());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			System.out.println("Het decoded password is: " + passwordConverter.decrypt(customer.getPassword()));
			mailSender.setTextMessage("registration");
			try {
				mailSender.sendMail(customer.getEmailAddress());
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
    	Customer customer = (Customer) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", customer.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void deleteCustomer(String email){
    	if(email!=null && email != ""){
    	Customer customer = customerRepository.findByEmail(email);
    	customerRepository.remove(customer.getId());
    	FacesMessage msg = new FacesMessage("Customer deleted", customer.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
        init();
    }
	
    public Customer getCustomer(int index){
		return customers.get(index);
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void addAction(){
    	customers.add(new Customer());
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
