package com.backend.srv.srv_email.controller;

import com.backend.srv.srv_email.dto.EmailRequest;
import com.backend.srv.srv_email.service.EmailService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;

@Tag(name = "Email", description = "Email Service")
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
        logger.info("EmailController initialized with EmailService");
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        logger.info("Received request to send email to: {}", emailRequest.getTo());
        try {
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            logger.info("Email sent successfully to: {}", emailRequest.getTo());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            logger.error("Failed to send email to: {}. Error: {}", emailRequest.getTo(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        }
    }
}
