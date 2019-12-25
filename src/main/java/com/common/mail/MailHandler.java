package com.common.mail;

import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class MailHandler {
	private JavaMailSender mailSender;
	private MimeMessage message;
	private MimeMessageHelper messageHelper;
	
	public MailHandler() {}
	
	@Autowired
	public MailHandler(JavaMailSender mailSender) throws MessagingException{
		this.mailSender = mailSender;
		message = this.mailSender.createMimeMessage();
		messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	}
	
	public void setSubject(String subject) throws MessagingException{
		messageHelper.setSubject(subject);
	}
	
	public void setText(String text) throws MessagingException{
		messageHelper.setText(text, true);
	}
	
	public void setFrom(String address, String name) throws UnsupportedEncodingException, MessagingException{
		messageHelper.setFrom(address, name);
	}
	
	public void setTo(String address, String name) throws MessagingException, UnsupportedEncodingException{
		messageHelper.setTo(new InternetAddress(address, name));
	}
	
	public void addInline(String contentId, DataSource dataSource) throws MessagingException{
		messageHelper.addInline(contentId, dataSource);
	}
	
	@Async
	public void send(){
		mailSender.send(message);
	}
}
