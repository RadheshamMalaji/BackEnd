package com.organicMart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailsender;
	
	public void sendEmailForNewRegistration(String email) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("orgmartindia@gmail.com");
        message.setTo(email); 
        message.setSubject("Thank you for Registering with us!"); 
        message.setText("Thank you for Registering with us!\n For Login Click Here: http://localhost:3000/login");
        mailsender.send(message);
	 }
	 
	 public void sendEmailForLogin(String email) {
			SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setFrom("orgmartindia@gmail.com");
	        message.setTo(email); 
	        message.setSubject("Account Login-Shipping Wars"); 
	        message.setText("Your account has been logged in , if not done by you then contact admin..!");
	        mailsender.send(message);
		 }
	
	public void sendMail(String toEmail,String subject,String body) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("orgmartindia@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		message.setText("Welcome to Organic Mart Offical website, We got your request for One Time Password(OTP) Verification. Here is your OTP : "+body);
		mailsender.send(message);
		System.out.println("mail send successfully");
	}
	
	 public void sendEmailForPayment(String email,int amt) {
			SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setFrom("orgmartindia@gmail.com");
	        message.setTo(email); 
	        message.setSubject("Payment SuccessFull"); 
	        message.setText("Payment SuccessFull of Amount : "+amt+"\n\nThank You For Shopping.\n keep shopping With us");
	        mailsender.send(message);
		 }
	
}
