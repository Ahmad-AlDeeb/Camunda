package org.paymentprocess;

import io.github.cdimascio.dotenv.Dotenv;

public class Constant {
    private final static Dotenv dotenv = Dotenv.load();

    public static final String ZEEBE_ADDRESS = dotenv.get("ZEEBE_ADDRESS");
    public static final String ZEEBE_CLIENT_ID = dotenv.get("ZEEBE_CLIENT_ID");
    public static final String ZEEBE_CLIENT_SECRET = dotenv.get("ZEEBE_CLIENT_SECRET");
    public static final String ZEEBE_AUTHORIZATION_SERVER_URL = dotenv.get("ZEEBE_AUTHORIZATION_SERVER_URL");
    public static final String ZEEBE_TOKEN_AUDIENCE = dotenv.get("ZEEBE_TOKEN_AUDIENCE");
}
