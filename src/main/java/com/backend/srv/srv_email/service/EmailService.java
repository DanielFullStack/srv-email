package com.backend.srv.srv_email.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        logger.info("EmailService initialized with JavaMailSender: {}", mailSender);
    }

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        logger.info("Attempting to send email to: {}, subject: {}", to, subject);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        try {
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to: {}", to, e);
            throw e;
        }
    }
}