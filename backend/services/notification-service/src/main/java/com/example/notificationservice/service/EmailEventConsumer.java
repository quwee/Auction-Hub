package com.example.notificationservice.service;

import com.example.shared.dto.event.EmailRegisterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.example.shared.messagequeueconfig.constants.AppMessageQueueConstants.EMAIL_PUBLISH_Q_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailEventConsumer {

    private final EmailNotificationService notificationService;

    @RabbitListener(queues = EMAIL_PUBLISH_Q_NAME)
    public void consumeRegisterEvent(EmailRegisterEvent event) {
        log.debug("consumeRegisterEvent: event: {}", event);

        notificationService.sendRegisterNotification(event);
    }

}
