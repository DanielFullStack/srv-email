package com.backend.srv.email.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OpenAPIConfigTests {

    @Autowired
    private OpenAPIConfig openAPIConfig;

    @Test
    public void testMyOpenAPIBean() {
        OpenAPI openAPI = openAPIConfig.myOpenAPI();
        
        assertNotNull(openAPI, "OpenAPI object should not be null");
        
        Info info = openAPI.getInfo();
        assertNotNull(info, "Info object should not be null");
        
        assertEquals("Email Service", info.getTitle(), "Title should match");
        assertEquals("1.0", info.getVersion(), "Version should match");
        assertEquals("API para serviço de envio de email.", info.getDescription(), "Description should match");
    }

    @Test
    public void testOpenAPIConfigCreation() {
        assertNotNull(openAPIConfig, "OpenAPIConfig should be created");
    }

    @Test
    public void testInfoObjectProperties() {
        OpenAPI openAPI = openAPIConfig.myOpenAPI();
        Info info = openAPI.getInfo();
        
        assertAll("Info properties",
            () -> assertNotNull(info.getTitle(), "Title should not be null"),
            () -> assertFalse(info.getTitle().isEmpty(), "Title should not be empty"),
            () -> assertNotNull(info.getVersion(), "Version should not be null"),
            () -> assertFalse(info.getVersion().isEmpty(), "Version should not be empty"),
            () -> assertNotNull(info.getDescription(), "Description should not be null"),
            () -> assertFalse(info.getDescription().isEmpty(), "Description should not be empty")
        );
    }
}
