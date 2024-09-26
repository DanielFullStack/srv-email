package com.backend.srv.srv_email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SrvEmailApplication {

	private final Logger logger = LogManager.getLogger(this.getClass());

	@Value("${logstash.host}")
    private String logstashHost;

    @Value("${logstash.port}")
    private String logstashPort;

	public static void main(String[] args) {
		SrvEmailApplication app = new SrvEmailApplication();
		app.run(args);
	}

	public void run(String[] args) {
		logger.info("Iniciando a aplicação de e-mail");
		SpringApplication.run(SrvEmailApplication.class, args);
		logger.info("Aplicação de e-mail iniciada com sucesso");
		logger.info("Logstash host: {}", logstashHost);
		logger.info("Logstash port: {}", logstashPort);
	}

}
