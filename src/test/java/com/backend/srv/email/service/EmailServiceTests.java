package com.backend.srv.email.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmailServiceTests {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        emailService = new EmailService(mailSender);
    }

    @Test
    void testSendEmail_Success() throws MessagingException {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Content";

        emailService.sendEmail(to, subject, text);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendEmail_FailureThrowsException() throws MessagingException {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Content";

        doThrow(new RuntimeException("Send failed")).when(mailSender).send(any(MimeMessage.class));

        assertThrows(RuntimeException.class, () -> emailService.sendEmail(to, subject, text));

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendEmail_NullRecipient() {
        String subject = "Test Subject";
        String text = "Test Content";

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(null, subject, text));
    }

    @Test
    void testSendEmail_EmptySubject() throws MessagingException {
        String to = "test@example.com";
        String text = "Test Content";

        emailService.sendEmail(to, "", text);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendEmail_NullContent() {
        String to = "test@example.com";
        String subject = "Test Subject";

        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(to, subject, null));
    }
}
