package com.backend.srv.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class EmailApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	private EmailApplication emailApplication;
	private Logger loggerMock;

	@BeforeEach
	void setUp() {
		emailApplication = new EmailApplication();
		loggerMock = mock(Logger.class);
		ReflectionTestUtils.setField(emailApplication, "logger", loggerMock);
		ReflectionTestUtils.setField(emailApplication, "logstashHost", "logstash");
		ReflectionTestUtils.setField(emailApplication, "logstashPort", "5000");
	}

	@Test
	void contextLoads() {
		assertNotNull(applicationContext, "Application context should not be null");
		assertTrue(applicationContext.containsBean("emailApplication"), "EmailApplication bean should be present");
		
		EmailApplication emailApp = applicationContext.getBean(EmailApplication.class);
		assertNotNull(emailApp, "EmailApplication bean should not be null");
		
		assertNotNull(ReflectionTestUtils.getField(emailApp, "logger"), "Logger should be initialized");
		assertEquals("logstash", ReflectionTestUtils.getField(emailApp, "logstashHost"), "Logstash host should be set");
		assertEquals("5000", ReflectionTestUtils.getField(emailApp, "logstashPort"), "Logstash port should be set");
	}

	@Test
	void testMainMethod() {
		String[] args = new String[]{};
		try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
			EmailApplication.main(args);

			mockedStatic.verify(() -> SpringApplication.run(EmailApplication.class, args));
		}
	}

	@Test
	void testRunMethod(CapturedOutput output) {
		String[] args = new String[]{};
		try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
			emailApplication.run(args);

			mockedStatic.verify(() -> SpringApplication.run(EmailApplication.class, args));
			verify(loggerMock).info("Iniciando a aplicação de e-mail");
			verify(loggerMock).info("Aplicação de e-mail iniciada com sucesso");
			verify(loggerMock).info("Logstash host: {}", "logstash");
			verify(loggerMock).info("Logstash port: {}", "5000");
		}
	}

	@Test
	void testLogstashConfiguration() {
		assertEquals("logstash", ReflectionTestUtils.getField(emailApplication, "logstashHost"));
		assertEquals("5000", ReflectionTestUtils.getField(emailApplication, "logstashPort"));
	}
}