package com.deeb.hiringprocess.exception;

import com.deeb.hiringprocess.util.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.deeb.hiringprocess.util.ApiResponseCreator.createUnifiedResponse;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handle(Exception exception) {
        return createUnifiedResponse(INTERNAL_SERVER_ERROR.value(), exception.getMessage(), null);
    }
}
