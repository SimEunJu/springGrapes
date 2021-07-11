package com.sej.grapes.error.exception;

import com.sej.grapes.error.ErrorCode;

public class JwtTokenExpireException extends JwtAuthenticationException{

    public JwtTokenExpireException(String msg, Throwable cause) {
        super(msg, ErrorCode.EXPIRED_TOKEN, cause);
    }

    public JwtTokenExpireException(String msg) {
        super(msg, ErrorCode.EXPIRED_TOKEN);
    }
}
