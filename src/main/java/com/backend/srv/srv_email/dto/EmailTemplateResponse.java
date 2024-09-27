package com.backend.srv.srv_email.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplateResponse {

    private String id;

    private String subject;

    private String templateBody;

    private Map<String, String> parameters;

}
