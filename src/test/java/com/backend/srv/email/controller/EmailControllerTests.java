package com.backend.srv.email.controller;

import com.backend.srv.email.dto.EmailRequest;
import com.backend.srv.email.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmailControllerTests {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmailSuccess() throws MessagingException {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setText("Test Content");

        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        ResponseEntity<String> response = emailController.sendEmail(emailRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email sent successfully", response.getBody());
        verify(emailService, times(1)).sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
    }

    @Test
    public void testSendEmailFailure() throws MessagingException {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setText("Test Content");

        String errorMessage = "Failed to send email";
        doThrow(new MessagingException(errorMessage)).when(emailService).sendEmail(anyString(), anyString(), anyString());

        ResponseEntity<String> response = emailController.sendEmail(emailRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to send email: " + errorMessage, response.getBody());
        verify(emailService, times(1)).sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
    }
}
