package com.backend.srv.srv_email.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.srv.srv_email.model.EmailTemplate;

public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {

    EmailTemplate findBySubject(String subject);

}
