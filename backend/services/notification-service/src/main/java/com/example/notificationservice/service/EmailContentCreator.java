package com.example.notificationservice.service;

import com.example.notificationservice.model.enums.TemplateType;
import com.example.shared.dto.event.EmailRegisterEvent;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static com.example.notificationservice.model.constants.TemplateAttributesNames.FULL_NAME;
import static com.example.notificationservice.model.constants.TemplateAttributesNames.URL;
import static com.example.notificationservice.model.enums.TemplateType.USER_REGISTER;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailContentCreator {

    private final Configuration configuration;

    @Value("${template.url.user-register}")
    private String userRegisterUrl;

    public String createContent(EmailRegisterEvent event) {
        log.debug("createContent: event: {}", event);

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(FULL_NAME, event.getFullName());
        attributes.put(URL, userRegisterUrl + event.getToken());

        String content = getEmailContent(USER_REGISTER, attributes);

        log.info("createContent: content: {}", content);

        return content;
    }

    private String getEmailContent(TemplateType templateType, Map<String, Object> attributes) {
        StringWriter writer = new StringWriter();
        try {
            configuration.getTemplate("%s.ftlh".formatted(templateType.name().toLowerCase()))
                    .process(attributes, writer);
            return writer.getBuffer().toString();
        }
        catch (Exception ex) {
            log.error("Error creating email content", ex);
            return null;
        }
    }

}
