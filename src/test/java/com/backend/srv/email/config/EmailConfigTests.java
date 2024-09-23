package com.backend.srv.email.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.TestPropertySource;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EmailConfig.class)
@TestPropertySource(properties = {
        "spring.mail.host=smtp.example.com",
        "spring.mail.port=587",
        "spring.mail.username=test@example.com",
        "spring.mail.password=testpassword",
        "spring.mail.protocol=smtp",
        "spring.mail.properties.mail.smtp.auth=true",
        "spring.mail.properties.mail.smtp.starttls.enable=true",
        "spring.mail.properties.mail.debug=true",
        "spring.mail.properties.mail.smtp.starttls.required=true",
        "spring.mail.properties.mail.smtp.ssl.enable=false",
        "spring.mail.properties.mail.smtp.connectiontimeout=5000",
        "spring.mail.properties.mail.smtp.timeout=3000",
        "spring.mail.properties.mail.smtp.writetimeout=5000"
})
public class EmailConfigTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void testJavaMailSenderConfiguration() {
        assertNotNull(javaMailSender);
        assertTrue(javaMailSender instanceof JavaMailSenderImpl);

        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;

        assertEquals("smtp.example.com", mailSender.getHost());
        assertEquals(587, mailSender.getPort());
        assertEquals("test@example.com", mailSender.getUsername());
        assertEquals("testpassword", mailSender.getPassword());
        assertEquals("smtp", mailSender.getProtocol());

        Properties props = mailSender.getJavaMailProperties();
        assertEquals("true", props.getProperty("mail.smtp.auth"));
        assertEquals("true", props.getProperty("mail.smtp.starttls.enable"));
        assertEquals("true", props.getProperty("mail.debug"));
        assertEquals("true", props.getProperty("mail.smtp.starttls.required"));
        assertEquals("false", props.getProperty("mail.smtp.ssl.enable"));
        assertEquals("5000", props.getProperty("mail.smtp.connectiontimeout"));
        assertEquals("3000", props.getProperty("mail.smtp.timeout"));
        assertEquals("5000", props.getProperty("mail.smtp.writetimeout"));
    }

    @Test
    public void testJavaMailSenderNotNull() {
        assertNotNull(javaMailSender);
    }

    @Test
    public void testJavaMailSenderType() {
        assertTrue(javaMailSender instanceof JavaMailSenderImpl);
    }

    @Test
    public void testMailSenderHost() {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
        assertEquals("smtp.example.com", mailSender.getHost());
    }

    @Test
    public void testMailSenderPort() {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
        assertEquals(587, mailSender.getPort());
    }

    @Test
    public void testMailSenderUsername() {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
        assertEquals("test@example.com", mailSender.getUsername());
    }

    @Test
    public void testMailSenderPassword() {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
        assertEquals("testpassword", mailSender.getPassword());
    }

    @Test
    public void testMailSenderProtocol() {
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
        assertEquals("smtp", mailSender.getProtocol());
    }

}
