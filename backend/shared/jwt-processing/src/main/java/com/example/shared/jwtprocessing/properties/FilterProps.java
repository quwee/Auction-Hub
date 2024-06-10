package com.example.shared.jwtprocessing.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("jwt-processing.filter")
@Data
public class FilterProps {
    private boolean enabled;
    private List<String> excludedPaths = new ArrayList<>();
}
