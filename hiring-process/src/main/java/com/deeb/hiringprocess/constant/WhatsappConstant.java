package com.deeb.hiringprocess.constant;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

public final class WhatsappConstant {

    private static final Dotenv DOTENV = Dotenv.load();

    public static final String WHATSAPP_API_URL = DOTENV.get("WHATSAPP_API_URL");
    public static final String WHATSAPP_TOKEN = DOTENV.get("WHATSAPP_TOKEN");
    public static final String WHATSAPP_RECEIVED_PHONE_NUMBER = DOTENV.get("WHATSAPP_RECEIVED_PHONE_NUMBER");
    public static final Map<String, Object> REQUEST_BODY = Map.of(
            "messaging_product", "whatsapp",
            "to", WHATSAPP_RECEIVED_PHONE_NUMBER,
            "type", "template",
            "template", Map.of(
                    "name", "hello_world",
                    "language", Map.of(
                            "code", "en_US"
                    )
            )
    );

    private WhatsappConstant() {
        throw new IllegalStateException("Utility class");
    }
}
