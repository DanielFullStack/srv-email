package com.backend.srv.srv_email.controller;

import com.backend.srv.srv_email.dto.EmailRequest;
import com.backend.srv.srv_email.dto.EmailTemplateResponse;
import com.backend.srv.srv_email.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;


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

    @Operation(summary = "Get all email templates", description = "Retrieves a list of all available email templates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved email templates"),
        @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @GetMapping("/templates")
    public ResponseEntity<List<EmailTemplateResponse>> getTemplates() {
        logger.info("Received request to get all email templates");
        try {
            List<EmailTemplateResponse> templates = emailService.getAllTemplates();
            logger.info("Successfully retrieved {} email templates", templates.size());
            return ResponseEntity.ok(templates);
        } catch (Exception e) {
            logger.error("Failed to retrieve email templates. Error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @Operation(summary = "Send email", description = "Sends an email based on the provided request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email sent successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid email request or failed to send email")
    })
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        logger.info("Received request to send email to: {}", emailRequest.getTo());
        try {
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getParameters());
            logger.info("Email sent successfully to: {}", emailRequest.getTo());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            logger.error("Failed to send email to: {}. Error: {}", emailRequest.getTo(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Failed to send email: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid email request for: {}. Error: {}", emailRequest.getTo(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("Invalid email request: " + e.getMessage());
        }
    }
}