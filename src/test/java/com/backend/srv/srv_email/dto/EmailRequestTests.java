package com.backend.srv.srv_email.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testEqualsAndHashCode() {
        EmailRequest emailRequest1 = new EmailRequest();
        emailRequest1.setTo("test@example.com");
        emailRequest1.setSubject("Test Subject");
        emailRequest1.setText("Test email content");

        EmailRequest emailRequest2 = new EmailRequest();
        emailRequest2.setTo("test@example.com");
        emailRequest2.setSubject("Test Subject");
        emailRequest2.setText("Test email content");

        assertEquals(emailRequest1, emailRequest2);
        assertEquals(emailRequest1.hashCode(), emailRequest2.hashCode());
    }

    @Test
    public void testToString() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setText("Test email content");

        String toString = emailRequest.toString();
        assertTrue(toString.contains("test@example.com"));
        assertTrue(toString.contains("Test Subject"));
        assertTrue(toString.contains("Test email content"));
    }
}
