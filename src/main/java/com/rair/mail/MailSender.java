package com.rair.mail;

import java.text.DecimalFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	private String textMessage;
	private String subject;

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
	private static final String EMAIL = "rairticketservice@gmail.com";
	private static final String PASSWORD = "rairtickets123";
	private static final String ACCOUNT = "BE84 1568 9425 4523";

	public MailSender() {
		super();
	}

	public String getTextMessage() {
		return textMessage = "";
	}

	public String getSubject() {
		return subject;
	}

	public void setTextMessage(String mail, Double price) {
		switch (mail) {
		case "confirmation":
			this.textMessage = "Dear Sir/Madam,<br/><br/>Thank you for your booking.<br/><br/>Kind regards, Rair Ticket Service";
			this.subject = "Booking confirmation";
		case "registration":
			this.textMessage = "Dear Sir/Madam,<br/><br/>Thank you for your registration. We hope to sell you lots of tickets!<br/><br/>Kind regards, Rair Ticket Service";
			this.subject = "New Rair Tickets Registration";
			break;
		case "employeeRegistration":
			this.textMessage = "Dear Sir/Madam,<br/><br/>Your account was created. From now on you can start managing pages at the admin.xhtml page.<br/<br/>Kind regards, Rair Ticket Service";
			this.subject = "New employee registration";
			break;
		case "partnerRegistration":
			this.textMessage = "Dear Sir/Madam,<br/><br/>Your account was created. From now on you can start managing flights at the flight.xhtml page.<br/><br/>Kind regards, Rair Ticket Service";
			this.subject = "New registration";
			break;
		case "endorsement":
			this.textMessage = "Dear Sir/Madam,<br/><br/>Thank you for your booking.<br/>Please remember your booking will only be final, after we have received your payment. Below you'll find the necessary payment information.<br/><br/>"
			+ "Account number: " + ACCOUNT + "<br/>Amount: " + new DecimalFormat("####0.00").format(price) + " €<br/><br/>Kind regards, Rair Ticketservice" ;
			this.subject = "Pending booking";
			break;
		default:
			break;
		}
	}

	public void sendMail(String recipient) throws AddressException, MessagingException {

		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		generateMailMessage.setSubject(subject);
		String emailBody = textMessage;
		generateMailMessage.setContent(emailBody, "text/html");

		Transport transport = getMailSession.getTransport("smtp");

		transport.connect("smtp.gmail.com", EMAIL, PASSWORD);
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}

}
