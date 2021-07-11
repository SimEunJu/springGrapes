package com.sej.grapes.error.exception;

import com.sej.grapes.error.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

    private ErrorCode errorCode;

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = ErrorCode.AUTHENTICATION_FAIL;
    }

    public JwtAuthenticationException(String msg, ErrorCode errorCode, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public JwtAuthenticationException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
