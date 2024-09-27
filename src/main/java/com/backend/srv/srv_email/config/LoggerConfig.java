package com.backend.srv.srv_email.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

        private final Logger logger = LogManager.getLogger(this.getClass());

        private final String mongodbUri;
        private final String sonarqubeHost;
        private final String sonarqubePort;
        private final String logstashHost;
        private final String logstashPort;

        public LoggerConfig(
                @Value("${spring.data.mongodb.uri}") String mongodbUri,
                @Value("${sonarqube.host}") String sonarqubeHost,
                @Value("${sonarqube.port}") String sonarqubePort,
                @Value("${logstash.host}") String logstashHost,
                @Value("${logstash.port}") String logstashPort) {
        
            this.mongodbUri = mongodbUri;
            this.sonarqubeHost = sonarqubeHost;
            this.sonarqubePort = sonarqubePort;
            this.logstashHost = logstashHost;
            this.logstashPort = logstashPort;

            logConfigurationDetails();
        }

        private void logConfigurationDetails() {
            logger.info("Initializing LoggerConfig with logstashHost: {} and logstashPort: {}", logstashHost, logstashPort);
            logger.info("MongoDB Configuration - URI: {}", mongodbUri);
            logger.info("SonarQube Configuration - Host: {}, Port: {}", sonarqubeHost, sonarqubePort);
        }
}