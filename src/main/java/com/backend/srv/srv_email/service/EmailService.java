package com.backend.srv.srv_email.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.backend.srv.srv_email.model.EmailTemplate;
import com.backend.srv.srv_email.repository.EmailTemplateRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.HashSet;

@Service
public class EmailService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final JavaMailSender mailSender;

    private final EmailTemplateRepository emailTemplateRepository;

    public EmailService(JavaMailSender mailSender, EmailTemplateRepository emailTemplateRepository) {
        this.mailSender = mailSender;
        this.emailTemplateRepository = emailTemplateRepository;
        logger.info("EmailService initialized with JavaMailSender: {}", mailSender);
    }

    public void sendEmail(String to, String subject, Map<String, String> parameters) throws MessagingException {
        logger.info("Attempting to send email to: {}, subject: {}", to, subject);
        EmailTemplate template = getEmailTemplate(subject);
        
        if (template == null) {
            logger.error("Email template not found for subject: {}", subject);
            throw new IllegalArgumentException("Email template not found");
        }

        validateParameters(template.getTemplateBody(), parameters);

        String emailContent = fillTemplate(template.getTemplateBody(), parameters);

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

    private void validateParameters(String template, Map<String, String> parameters) {
        Set<String> expectedParams = extractParameters(template);
        Set<String> providedParams = parameters.keySet();

        if (!expectedParams.equals(providedParams)) {
            Set<String> missingParams = new HashSet<>(expectedParams);
            missingParams.removeAll(providedParams);
            Set<String> extraParams = new HashSet<>(providedParams);
            extraParams.removeAll(expectedParams);

            String errorMessage = "Parameter mismatch. ";
            if (!missingParams.isEmpty()) {
                errorMessage += "Missing parameters: " + missingParams + ". ";
            }
            if (!extraParams.isEmpty()) {
                errorMessage += "Extra parameters: " + extraParams + ". ";
            }
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private Set<String> extractParameters(String template) {
        Set<String> params = new HashSet<>();
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            params.add(matcher.group(1));
        }
        return params;
    }

    private String fillTemplate(String template, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }
}