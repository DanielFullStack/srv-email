package com.backend.srv.srv_email.dto;

import java.util.Map;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String text;
    private Map<String, String> parameters;

}