package com.jehko.jpa.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	public void sendMail(String to, String subject, String contents) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(contents);
		
		mailSender.send(mail);
	}
}
