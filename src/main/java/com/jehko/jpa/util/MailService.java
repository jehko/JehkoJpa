package com.jehko.jpa.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String admin;
	
	public void sendSimpleMail(String to, String subject, String contents) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(admin);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(contents);
		
		mailSender.send(mail);
	}

	public boolean sendMail(String fromName,
							String toEmail, String toName,
							String title, String contents) {

		boolean result = false;

		MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				InternetAddress from = new InternetAddress();
				from.setPersonal(fromName);

				InternetAddress to = new InternetAddress();
				to.setAddress(toEmail);
				to.setPersonal(toName);

				mimeMessageHelper.setFrom(from);
				mimeMessageHelper.setTo(to);
				mimeMessageHelper.setSubject(title);
				mimeMessageHelper.setText(contents);
			}
		};
		try {
			mailSender.send(mimeMessagePreparator);
			result = true;
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		return result;
	}
}
