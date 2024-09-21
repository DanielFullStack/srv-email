package com.backend.srv.email.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;

@Configuration
public class EmailConfig {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private String mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.protocol}")
    private String mailProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttlsEnable;

    @Value("${spring.mail.properties.mail.debug}")
    private String mailDebug;

    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private String starttlsRequired;

    @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
    private String sslEnable;

    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private String connectionTimeout;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private String timeout;

    @Value("${spring.mail.properties.mail.smtp.writetimeout}")
    private String writeTimeout;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.parseInt(mailPort));
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);
        mailSender.setProtocol(mailProtocol);

        Properties props = mailSender.getJavaMailProperties();
        setMailProperties(props);

        mailSender.setJavaMailProperties(props);

        // Log das propriedades do mailSender
        logger.info("Mail Sender Properties:");
        logger.info("Host: " + mailSender.getHost());
        logger.info("Port: " + mailSender.getPort());
        logger.info("Username: " + mailSender.getUsername());
        logger.info("Protocol: " + mailSender.getProtocol());

        // Log das propriedades de props
        logger.info("\nMail Properties:");
        props.forEach((key, value) -> logger.info(key + ": " + value));

        return mailSender;
    }

    private void setMailProperties(Properties props) {
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.starttls.required", starttlsRequired);
        props.put("mail.smtp.ssl.enable", sslEnable);
        props.put("mail.debug", mailDebug);
        props.put("mail.smtp.connectiontimeout", Integer.parseInt(connectionTimeout));
        props.put("mail.smtp.timeout", Integer.parseInt(timeout));
        props.put("mail.smtp.writetimeout", Integer.parseInt(writeTimeout));
    }
}
