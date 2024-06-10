package com.example.auctionservice.config;

import com.example.shared.exceptionhandling.handler.AppGlobalExceptionHandler;
import com.example.shared.jwtprocessing.config.JwtProcessingConfig;
import com.example.shared.messagequeueconfig.config.AppMessageQueueConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Import({AppMessageQueueConfig.class})
@ImportAutoConfiguration({JwtProcessingConfig.class, AppGlobalExceptionHandler.class})
public class AppConfig {
}
