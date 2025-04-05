package com.deeb.hiringprocess.Constant;

import io.github.cdimascio.dotenv.Dotenv;

import static com.deeb.hiringprocess.Constant.ExceptionConstant.UTILITY_CLASS_INSTANTIATION_MESSAGE;

public final class CamundaConstant {
    private final static Dotenv dotenv = Dotenv.load();

    public static final String ZEEBE_REST_ADDRESS = dotenv.get("ZEEBE_REST_ADDRESS");
    public static final String ZEEBE_TOKEN = dotenv.get("ZEEBE_TOKEN");
    public static final String TASKLIST_TOKEN = dotenv.get("TASKLIST_TOKEN");
    public static final String ZEEBE_ADDRESS = dotenv.get("ZEEBE_ADDRESS");
    public static final String ZEEBE_CLIENT_ID = dotenv.get("ZEEBE_CLIENT_ID");
    public static final String ZEEBE_CLIENT_SECRET = dotenv.get("ZEEBE_CLIENT_SECRET");
    public static final String ZEEBE_AUTHORIZATION_SERVER_URL = dotenv.get("ZEEBE_AUTHORIZATION_SERVER_URL");
    public static final String ZEEBE_TOKEN_AUDIENCE = dotenv.get("ZEEBE_TOKEN_AUDIENCE");

    public static final String PROCESS_DEFINITION_ID = "Process_Hiring";

    private CamundaConstant() {
        throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
