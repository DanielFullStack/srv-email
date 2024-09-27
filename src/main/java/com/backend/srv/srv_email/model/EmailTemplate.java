package com.backend.srv.srv_email.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "email_templates")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailTemplate {

    @Id
    private String id;

    private String subject;

    private String templateBody;

    private List<String> parameters;

}
