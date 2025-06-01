package com.deeb.hiringprocess.sendgrid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "sendgrid")
class SendGridConfigurationProperties {
    @NotBlank
    private String apiKey;

    @NotBlank
    @Email
    private String fromEmail;

    @NotBlank
    private String fromName;
}


