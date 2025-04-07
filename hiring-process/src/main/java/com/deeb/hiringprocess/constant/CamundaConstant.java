package com.deeb.hiringprocess.constant;

import io.github.cdimascio.dotenv.Dotenv;

import static com.deeb.hiringprocess.constant.ExceptionConstant.UTILITY_CLASS_INSTANTIATION_MESSAGE;

public final class CamundaConstant {
    private static final Dotenv DOTENV = Dotenv.load();

    public static final String ZEEBE_REST_ADDRESS = DOTENV.get("ZEEBE_REST_ADDRESS");
    public static final String ZEEBE_TOKEN = DOTENV.get("ZEEBE_TOKEN");
    public static final String OPERATE_BASE_URL = DOTENV.get("OPERATE_BASE_URL");
    public static final String OPERATE_TOKEN = DOTENV.get("OPERATE_TOKEN");

    public static final String PROCESS_DEFINITION_ID = "Process_Hiring";

    private CamundaConstant() {
        throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
