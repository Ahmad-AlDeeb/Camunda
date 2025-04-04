package com.deeb.hiringprocess.Constant;

import static com.deeb.hiringprocess.Constant.ExceptionConstant.UTILITY_CLASS_INSTANTIATION_MESSAGE;

public final class ApiResponseConstant {
    public static final String RESOURCE_CREATED = "%s created!";
    public static final String RESOURCE_FOUND = "%s found!";
    public static final String RESOURCES_RETURNED = "All %ss are returned!";
    public static final String RESOURCE_UPDATED = "%s updated!";
    public static final String RESOURCE_DELETED = "%s deleted!";

    private ApiResponseConstant() {
        throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
