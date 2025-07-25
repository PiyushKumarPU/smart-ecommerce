package com.instaprepsai.common.exception.handler;

import com.instaprepsai.common.api.response.InstaprepsApiResponse;
import com.instaprepsai.common.exception.BusinessException;
import com.instaprepsai.common.exception.enums.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<InstaprepsApiResponse<?>> handleGlobalException(BusinessException exception) {
        ExceptionType exceptionType = exception.getExceptionType();
        HttpStatusCode statusCode =
                exceptionType.getStatus() != null ? exceptionType.getStatus() : HttpStatus.BAD_REQUEST;
        InstaprepsApiResponse<?> result =
                exception.getApiResponse() != null ? exception.getApiResponse() : new InstaprepsApiResponse<>();

        if (exception.getValidationErrors() != null && !exception.getValidationErrors().isEmpty()) {
            statusCode = HttpStatus.BAD_REQUEST;
            result = new InstaprepsApiResponse<>(exception.getValidationErrors(), exceptionType.getDescription());
        }
        return ResponseEntity.status(statusCode).body(result);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<InstaprepsApiResponse<?>> handleException(Exception exception) {
        return ResponseEntity.badRequest()
                .body(InstaprepsApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage()));
    }
}
