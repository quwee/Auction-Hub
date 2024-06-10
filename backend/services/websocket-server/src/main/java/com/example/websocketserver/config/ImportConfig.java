package com.example.websocketserver.config;

import com.example.shared.jwtprocessing.config.JwtProcessingConfig;
import com.example.shared.messagequeueconfig.config.AppMessageQueueConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppMessageQueueConfig.class})
@ImportAutoConfiguration({JwtProcessingConfig.class})
public class ImportConfig {
}