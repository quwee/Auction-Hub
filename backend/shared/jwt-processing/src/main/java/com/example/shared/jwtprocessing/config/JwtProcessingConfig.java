package com.example.shared.jwtprocessing.config;

import com.example.shared.jwtprocessing.filter.JwtAuthFilter;
import com.example.shared.jwtprocessing.properties.FilterProps;
import com.example.shared.jwtprocessing.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.example.shared.jwtprocessing"})
@RequiredArgsConstructor
public class JwtProcessingConfig {

    private final FilterProps filterProps;

    @Bean
    @ConditionalOnBean(HandlerExceptionResolver.class)
    @ConditionalOnProperty(name = "jwt-processing.filter.enabled", havingValue = "true")
    public JwtAuthFilter jwtAuthFilter(JwtService jwtService, HandlerExceptionResolver handlerExceptionResolver) {
        return new JwtAuthFilter(jwtService, handlerExceptionResolver, filterProps.getExcludedPaths());
    }

}
