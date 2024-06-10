package com.example.notificationservice.config;

import com.example.shared.messagequeueconfig.config.AppMessageQueueConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppMessageQueueConfig.class})
public class ImportConfig {
}
