package com.backend.srv.srv_email.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplateResponse {

    private String subject;

    private String templateBody;

    private List<String> parameters;

}
