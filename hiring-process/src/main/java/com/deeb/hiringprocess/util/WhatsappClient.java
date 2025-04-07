package com.deeb.hiringprocess.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.deeb.hiringprocess.constant.WhatsappConstant.REQUEST_BODY;
import static com.deeb.hiringprocess.constant.WhatsappConstant.WHATSAPP_API_URL;
import static com.deeb.hiringprocess.constant.WhatsappConstant.WHATSAPP_TOKEN;

@Component
public class WhatsappClient {
    private final RestClient restClient;

    public WhatsappClient() {
        restClient = RestClient.builder()
                .baseUrl(WHATSAPP_API_URL)
                .defaultHeader("Authorization", "Bearer " + WHATSAPP_TOKEN)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String sendMessage() {
        return restClient.post()
                .uri("/messages")
                .body(REQUEST_BODY)
                .retrieve()
                .body(String.class);
    }
}
