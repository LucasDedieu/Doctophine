package fr.dauphine.mido.doctophine.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
	private static final MailService INSTANCE = new MailService();
	private Properties props = new Properties(); 
	private String password;
	private String login;


	private MailService() {
		init();
	}

	public static MailService getInstance() {
		return INSTANCE;
	}

	private void init() {
		props.put("mail.smtp.host", "smtp.gmail.com"); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.port", "587"); 
		props.put("mail.smtp.starttls.enable", "true"); 
		props.put("mail.smtp.ssl.trust", "*");
		login="ne.pas.repondre.doctophine@gmail.com";
		password="JavaApp2021";
	}


	/**
	 * Send mail 
	 * @param subject
	 * @param text
	 * @param recipient
	 */
	public void sendMail(String subject, String text, String recipient) {

		Session session = Session.getDefaultInstance(props,  
				new javax.mail.Authenticator() {  
			protected PasswordAuthentication getPasswordAuthentication() {  
				return new PasswordAuthentication(login,password);  
			}  
		});  

		try {  
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(login));  
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));  
			message.setSubject("[Doctophine] "+subject);  
			message.setText(text);


			Transport.send(message);  

			System.out.println("message sent!");  

		} 
		catch (MessagingException mex) 
		{
			System.out.println("Error: unable to send message....");
			mex.printStackTrace();
		}

	}
}
