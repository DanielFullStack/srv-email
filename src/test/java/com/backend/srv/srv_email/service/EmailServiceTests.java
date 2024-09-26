package com.backend.srv.srv_email.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import com.backend.srv.srv_email.repository.EmailTemplateRepository;
import com.backend.srv.srv_email.model.EmailTemplate;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class EmailServiceTests {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailTemplateRepository emailTemplateRepository;

    @Mock
    private MimeMessage mimeMessage;

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        emailService = new EmailService(mailSender, emailTemplateRepository);
    }

    @Test
    void testSendEmail_Success() throws MessagingException {
        String to = "test@example.com";
        String subject = "Test Subject";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        EmailTemplate mockTemplate = new EmailTemplate();
        mockTemplate.setTemplateBody("Test Content {{key}}");
        when(emailTemplateRepository.findBySubject(subject)).thenReturn(mockTemplate);

        emailService.sendEmail(to, subject, parameters);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendEmail_FailureThrowsException() throws MessagingException {
        String to = "test@example.com";
        String subject = "Test Subject";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        EmailTemplate mockTemplate = new EmailTemplate();
        mockTemplate.setTemplateBody("Test Content {{key}}");
        when(emailTemplateRepository.findBySubject(subject)).thenReturn(mockTemplate);

        doThrow(new RuntimeException("Send failed")).when(mailSender).send(any(MimeMessage.class));

        assertThrows(RuntimeException.class, () -> emailService.sendEmail(to, subject, parameters));

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendEmail_NullRecipient() {
        String subject = "Test Subject";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(null, subject, parameters));
    }

    @Test
    void testSendEmail_EmptySubject() {
        String to = "test@example.com";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(to, "", parameters));
    }

    @Test
    void testSendEmail_NullParameters() {
        String to = "test@example.com";
        String subject = "Test Subject";

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(to, subject, null));
    }

    @Test
    void testSendEmail_TemplateNotFound() {
        String to = "test@example.com";
        String subject = "Non-existent Subject";
        Map<String, String> parameters = new HashMap<>();

        when(emailTemplateRepository.findBySubject(subject)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(to, subject, parameters));
    }

    @Test
    void testSendEmail_ParameterMismatch() {
        String to = "test@example.com";
        String subject = "Test Subject";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("wrongKey", "value");

        EmailTemplate mockTemplate = new EmailTemplate();
        mockTemplate.setTemplateBody("Test Content {{key}}");
        when(emailTemplateRepository.findBySubject(subject)).thenReturn(mockTemplate);

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(to, subject, parameters));
    }

    @Test
    void testSendEmail_ExtraParameters() {
        String to = "test@example.com";
        String subject = "Test Subject";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");
        parameters.put("extraKey", "extraValue");

        EmailTemplate mockTemplate = new EmailTemplate();
        mockTemplate.setTemplateBody("Test Content {{key}}");
        when(emailTemplateRepository.findBySubject(subject)).thenReturn(mockTemplate);

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(to, subject, parameters));
    }
}
