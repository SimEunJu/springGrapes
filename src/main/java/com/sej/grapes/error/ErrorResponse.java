package com.sej.grapes.error;

import lombok.*;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ErrorResponse {

    private String code;
    private int status;
    private String message;
    private List<FieldError> errors;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errors = errors;
    }

}
