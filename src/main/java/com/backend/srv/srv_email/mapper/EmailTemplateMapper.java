package com.backend.srv.srv_email.mapper;

import com.backend.srv.srv_email.model.EmailTemplate;
import com.backend.srv.srv_email.dto.EmailTemplateResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class EmailTemplateMapper {

    public List<EmailTemplateResponse> mapToEmailTemplateResponseList(List<EmailTemplate> templates) {
        return templates.stream()
            .map(this::mapToEmailTemplateResponse)
            .collect(Collectors.toList());
    }

    private EmailTemplateResponse mapToEmailTemplateResponse(EmailTemplate template) {
        return new EmailTemplateResponse(
            template.getSubject(),
            template.getTemplateBody(),
            extractParameters(template.getTemplateBody()).stream()
                .collect(Collectors.toList())
        );
    }

    public void validateParameters(String template, Map<String, String> parameters) {
        Set<String> expectedParams = extractParameters(template);
        Set<String> providedParams = parameters.keySet();

        if (!expectedParams.equals(providedParams)) {
            Set<String> missingParams = new HashSet<>(expectedParams);
            missingParams.removeAll(providedParams);
            Set<String> extraParams = new HashSet<>(providedParams);
            extraParams.removeAll(expectedParams);

            String errorMessage = "Parameter mismatch. ";
            if (!missingParams.isEmpty()) {
                errorMessage += "Missing parameters: " + missingParams + ". ";
            }
            if (!extraParams.isEmpty()) {
                errorMessage += "Extra parameters: " + extraParams + ". ";
            }
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public Set<String> extractParameters(String template) {
        Set<String> params = new HashSet<>();
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            params.add(matcher.group(1));
        }
        return params;
    }

    public String fillTemplate(String template, Map<String, String> parameters) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }

}
