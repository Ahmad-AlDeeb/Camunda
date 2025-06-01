package com.deeb.hiringprocess.constant;

public final class MessageConstant {
    public static final String UTILITY_CLASS_INSTANTIATION_MESSAGE = "Utility class should not be instantiated!";
    private MessageConstant() {
        throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
