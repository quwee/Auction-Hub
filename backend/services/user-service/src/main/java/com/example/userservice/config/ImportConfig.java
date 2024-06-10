package com.example.userservice.config;

import com.example.shared.exceptionhandling.handler.AppGlobalExceptionHandler;
import com.example.shared.jwtprocessing.config.JwtProcessingConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ImportAutoConfiguration({JwtProcessingConfig.class, AppGlobalExceptionHandler.class})
public class ImportConfig {
}
