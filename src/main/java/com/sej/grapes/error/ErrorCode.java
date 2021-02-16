package com.sej.grapes.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Method Not Allowed"),
    MISSING_REQUIRED_INPUT_VALUE(400, "C003", "Missing Required Input Value"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    REQUEST_RESOURCE_NOT_EXIST(404, "C005", "Request Resource Is Not Exist"),
    ACCESS_DENIED(403, "C006", "Access is Denied");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message){
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
