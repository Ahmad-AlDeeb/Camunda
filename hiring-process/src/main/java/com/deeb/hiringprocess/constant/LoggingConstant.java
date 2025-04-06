package com.deeb.hiringprocess.constant;

public final class LoggingConstant {
    public static final String BEFORE_MESSAGE = "Executing ===> {}.{} with arguments: [{}]";
    public static final String AFTER_RETURN_MESSAGE = "Finished ===> {}.{} with arguments: [{}] and returned {}";
    public static final String AFTER_THROW_MESSAGE = "Exception {} in ===> {}.{} with arguments: [{}]";

    private LoggingConstant() {
        throw new IllegalStateException(ExceptionConstant.UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
