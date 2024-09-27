package com.backend.srv.srv_email.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

        private final Logger logger = LogManager.getLogger(this.getClass());

        private final String mongodbHost;
        private final String mongodbPort;
        private final String mongodbDatabase;
        private final String mongodbUsername;
        private final String mongodbPassword;
        private final String sonarqubeHost;
        private final String sonarqubePort;
        private final String logstashHost;
        private final String logstashPort;

        public LoggerConfig(
                @Value("${spring.data.mongodb.host}") String mongodbHost,
                @Value("${spring.data.mongodb.port}") String mongodbPort,
                @Value("${spring.data.mongodb.database}") String mongodbDatabase,
                @Value("${spring.data.mongodb.username}") String mongodbUsername,
                @Value("${spring.data.mongodb.password}") String mongodbPassword,
                @Value("${sonarqube.host}") String sonarqubeHost,
                @Value("${sonarqube.port}") String sonarqubePort,
                @Value("${logstash.host}") String logstashHost,
                @Value("${logstash.port}") String logstashPort) {
        
            this.mongodbHost = mongodbHost;
            this.mongodbPort = mongodbPort;
            this.mongodbDatabase = mongodbDatabase;
            this.mongodbUsername = mongodbUsername;
            this.mongodbPassword = mongodbPassword;
            this.sonarqubeHost = sonarqubeHost;
            this.sonarqubePort = sonarqubePort;
            this.logstashHost = logstashHost;
            this.logstashPort = logstashPort;

            logConfigurationDetails();
        }

        private void logConfigurationDetails() {
            logger.info("Initializing LoggerConfig with logstashHost: {} and logstashPort: {}", logstashHost, logstashPort);
            logger.info("MongoDB Configuration - Host: {}, Port: {}, Database: {}, Username: {}, Password: {}", mongodbHost, mongodbPort,
                    mongodbDatabase, mongodbUsername, mongodbPassword);
            logger.info("SonarQube Configuration - Host: {}, Port: {}", sonarqubeHost, sonarqubePort);
        }
}