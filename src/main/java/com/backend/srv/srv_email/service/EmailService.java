package com.backend.srv.srv_email.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.backend.srv.srv_email.model.EmailTemplate;
import com.backend.srv.srv_email.repository.EmailTemplateRepository;
import com.backend.srv.srv_email.dto.EmailTemplateResponse;
import com.backend.srv.srv_email.mapper.EmailTemplateMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Map;
import java.util.List;

@Service
public class EmailService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final EmailTemplateMapper emailTemplateMapper;

    private final JavaMailSender mailSender;

    private final EmailTemplateRepository emailTemplateRepository;

    public EmailService(EmailTemplateMapper emailTemplateMapper, JavaMailSender mailSender, EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateMapper = emailTemplateMapper;
        this.mailSender = mailSender;
        this.emailTemplateRepository = emailTemplateRepository;
        logger.info("EmailService initialized with JavaMailSender: {}", mailSender);
    }

    public List<EmailTemplateResponse> getAllTemplates() {
        List<EmailTemplate> templates = emailTemplateRepository.findAll();
        return emailTemplateMapper.mapToEmailTemplateResponseList(templates);
    }

    public void sendEmail(String to, String subject, Map<String, String> parameters) throws MessagingException {
        logger.info("Attempting to send email to: {}, subject: {}", to, subject);
        EmailTemplate template = getEmailTemplate(subject);
        
        if (template == null) {
            logger.error("Email template not found for subject: {}", subject);
            throw new IllegalArgumentException("Email template not found");
        }

        emailTemplateMapper.validateParameters(template.getTemplateBody(), parameters);

        String emailContent = emailTemplateMapper.fillTemplate(template.getTemplateBody(), parameters);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(emailContent, true);

        try {
            mailSender.send(message);
            logger.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send email to: {}", to, e);
            throw e;
        }
    }

    private EmailTemplate getEmailTemplate(String subject) {
        return emailTemplateRepository.findBySubject(subject);
    }

}