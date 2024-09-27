package com.backend.srv.srv_email.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import com.backend.srv.srv_email.repository.EmailTemplateRepository;
import com.backend.srv.srv_email.model.EmailTemplate;
import com.backend.srv.srv_email.dto.EmailTemplateResponse;
import com.backend.srv.srv_email.mapper.EmailTemplateMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class EmailServiceTests {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailTemplateRepository emailTemplateRepository;

    @Mock
    private EmailTemplateMapper emailTemplateMapper;

    @Mock
    private MimeMessage mimeMessage;

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        emailService = new EmailService(emailTemplateMapper, mailSender, emailTemplateRepository);
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
        when(emailTemplateMapper.fillTemplate(mockTemplate.getTemplateBody(), parameters)).thenReturn("Test Content value");

        emailService.sendEmail(to, subject, parameters);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(any(MimeMessage.class));
        verify(emailTemplateMapper, times(1)).validateParameters(mockTemplate.getTemplateBody(), parameters);
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
        when(emailTemplateMapper.fillTemplate(mockTemplate.getTemplateBody(), parameters)).thenReturn("Test Content value");

        doThrow(new RuntimeException("Send failed")).when(mailSender).send(any(MimeMessage.class));

        assertThrows(RuntimeException.class, () -> emailService.sendEmail(to, subject, parameters));

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(any(MimeMessage.class));
        verify(emailTemplateMapper, times(1)).validateParameters(mockTemplate.getTemplateBody(), parameters);
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
    void testGetAllTemplates() {
        EmailTemplate template1 = new EmailTemplate();
        template1.setId("65a7b9c83f2d1e4b8c0f2d3e");
        template1.setSubject("Subject 1");
        template1.setTemplateBody("Body 1 {{param1}}");

        EmailTemplate template2 = new EmailTemplate();
        template2.setId("65a7b9c83f2d1e4b8c0f2d3f");
        template2.setSubject("Subject 2");
        template2.setTemplateBody("Body 2 {{param2}}");

        List<EmailTemplate> templates = Arrays.asList(template1, template2);
        when(emailTemplateRepository.findAll()).thenReturn(templates);

        List<EmailTemplateResponse> expectedResponses = Arrays.asList(
            new EmailTemplateResponse("65a7b9c83f2d1e4b8c0f2d3e", "Subject 1", "Body 1 {{param1}}", Map.of("param1", "")),
            new EmailTemplateResponse("65a7b9c83f2d1e4b8c0f2d3f", "Subject 2", "Body 2 {{param2}}", Map.of("param2", ""))
        );
        when(emailTemplateMapper.mapToEmailTemplateResponseList(templates)).thenReturn(expectedResponses);

        List<EmailTemplateResponse> result = emailService.getAllTemplates();

        assertEquals(2, result.size());
        assertEquals("Subject 1", result.get(0).getSubject());
        assertEquals("Body 1 {{param1}}", result.get(0).getTemplateBody());
        assertEquals(1, result.get(0).getParameters().size());
        assertTrue(result.get(0).getParameters().containsKey("param1"));
        assertEquals("Subject 2", result.get(1).getSubject());
        assertEquals("Body 2 {{param2}}", result.get(1).getTemplateBody());
        assertEquals(1, result.get(1).getParameters().size());
        assertTrue(result.get(1).getParameters().containsKey("param2"));

        verify(emailTemplateRepository, times(1)).findAll();
        verify(emailTemplateMapper, times(1)).mapToEmailTemplateResponseList(templates);
    }
}
