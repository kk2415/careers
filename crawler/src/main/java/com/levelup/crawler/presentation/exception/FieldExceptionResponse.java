package com.levelup.crawler.presentation.exception;

import com.levelup.common.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.List;

@Getter
public class FieldExceptionResponse extends ExceptionResponse {

    private final List<FieldError> fieldErrors;

    private FieldExceptionResponse(ExceptionCode exceptionCode, List<FieldError> fieldErrors) {
        super(exceptionCode);
        this.fieldErrors = fieldErrors;
    }

    public FieldExceptionResponse(String code, String message, int httpStatus, List<FieldError> fieldErrors) {
        super(code, message, httpStatus);
        this.fieldErrors = fieldErrors;
    }

    public static FieldExceptionResponse of(ExceptionCode exceptionCode, BindingResult bindingResult) {
        return new FieldExceptionResponse(exceptionCode, FieldError.of(bindingResult));
    }

    public static FieldExceptionResponse of(String code, String message, int httpStatus, BindingResult bindingResult) {
        return new FieldExceptionResponse(code, message, httpStatus, FieldError.of(bindingResult));
    }

    @Getter
    public static class FieldError {

        private final String field;
        private final String value;
        private final String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()
                    )).toList();
        }
    }
}
