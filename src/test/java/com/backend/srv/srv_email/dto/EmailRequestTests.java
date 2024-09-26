package com.backend.srv.srv_email.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class EmailRequestTests {

    @Test
    public void testEmailRequestConstructor() {
        EmailRequest emailRequest = new EmailRequest();
        assertNotNull(emailRequest);
    }

    @Test
    public void testSetAndGetTo() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo("test@example.com");
        assertEquals("test@example.com", emailRequest.getTo());
    }

    @Test
    public void testSetAndGetSubject() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject("Test Subject");
        assertEquals("Test Subject", emailRequest.getSubject());
    }

    @Test
    public void testSetAndGetText() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setText("Test email content");
        assertEquals("Test email content", emailRequest.getText());
    }

    @Test
    public void testSetAndGetParameters() {
        EmailRequest emailRequest = new EmailRequest();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key1", "value1");
        parameters.put("key2", "value2");
        emailRequest.setParameters(parameters);
        assertEquals(parameters, emailRequest.getParameters());
    }

    @Test
    public void testEqualsAndHashCode() {
        EmailRequest emailRequest1 = new EmailRequest();
        emailRequest1.setTo("test@example.com");
        emailRequest1.setSubject("Test Subject");
        emailRequest1.setText("Test email content");
        Map<String, String> parameters1 = new HashMap<>();
        parameters1.put("key1", "value1");
        emailRequest1.setParameters(parameters1);

        EmailRequest emailRequest2 = new EmailRequest();
        emailRequest2.setTo("test@example.com");
        emailRequest2.setSubject("Test Subject");
        emailRequest2.setText("Test email content");
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("key1", "value1");
        emailRequest2.setParameters(parameters2);

        assertEquals(emailRequest1, emailRequest2);
        assertEquals(emailRequest1.hashCode(), emailRequest2.hashCode());
    }

    @Test
    public void testToString() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setText("Test email content");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("key1", "value1");
        emailRequest.setParameters(parameters);

        String toString = emailRequest.toString();
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("Test Subject"));
        assertTrue(toString.contains("Test email content"));
        assertTrue(toString.contains("key1"));
        assertTrue(toString.contains("value1"));
    }
}
