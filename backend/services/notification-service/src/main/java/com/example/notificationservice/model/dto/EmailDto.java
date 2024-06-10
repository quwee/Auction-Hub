package com.example.notificationservice.model.dto;

import com.example.notificationservice.model.enums.TemplateType;

public record EmailDto(String recipient, String subject, String content) {

    @Override
    public String toString() {
        return "EmailDto{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
