package com.backend.srv.srv_email.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "email_templates")
@Data
public class EmailTemplate {

    @Id
    private String id;

    private String subject;

    private String templateBody;

    public EmailTemplate() {}

    public EmailTemplate(String subject, String templateBody) {
        this.subject = subject;
        this.templateBody = templateBody;
    }

}
