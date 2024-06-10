package com.example.authservice.service;

import com.example.shared.dto.event.EmailRegisterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import static com.example.shared.messagequeueconfig.constants.AppMessageQueueConstants.EMAIL_PUBLISH_Q_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailEventSender {

    private final AmqpTemplate amqpTemplate;

    public void sendRegisterEvent(EmailRegisterEvent event) {
        log.debug("sendRegisterEvent: event: {}", event);

        amqpTemplate.convertAndSend(EMAIL_PUBLISH_Q_NAME, event);
    }

}
