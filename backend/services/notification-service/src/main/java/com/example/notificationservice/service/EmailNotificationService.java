package com.example.notificationservice.service;

import com.example.notificationservice.model.dto.EmailDto;
import com.example.shared.dto.event.EmailRegisterEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final EmailContentCreator emailContentCreator;
    private final EmailSender emailSender;

    public void sendRegisterNotification(EmailRegisterEvent event) {
        log.debug("sendRegisterNotification: event: {}", event);

        String content = emailContentCreator.createContent(event);

        if (content == null) {
            return;
        }

        var emailDto = new EmailDto(event.getEmail(), "Registration", content);

        try {
            emailSender.sendEmail(emailDto);
            log.debug("sendRegisterNotification: message is sent");
        } catch (MessagingException ex) {
            log.error("Failed to send email", ex);
        }
    }

}
