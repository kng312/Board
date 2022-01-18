package com.board.domain;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

	@Component
	public class EmailSender  {
	     
	    @Autowired
	    protected JavaMailSender  mailSender;
	 
	    public void SendEmail(Email email) throws Exception {
	         
	        MimeMessage msg = mailSender.createMimeMessage();
	        msg.setSubject(email.getSubject());
	        msg.setText(email.getContent());
	        InternetAddress [] toadd = new InternetAddress[2];
	        toadd[0]= new InternetAddress("kng312@naver.com");
	        toadd[1]= new InternetAddress("qkh1312@daum.net");
	       // toadd[2]= new InternetAddress("kyz9880@naver.com");
	       // toadd[3]= new InternetAddress("okdol@bow-tech.co.kr");
	       // toadd[4]= new InternetAddress("airtrade7@naver.com");
	        
	        
	        
	        msg.setRecipients(RecipientType.TO , toadd);
	         
	        mailSender.send(msg); 
	    }
	}

