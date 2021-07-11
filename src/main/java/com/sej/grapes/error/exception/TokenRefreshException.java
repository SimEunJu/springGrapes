package com.sej.grapes.error.exception;

import com.sej.grapes.error.ErrorCode;

public class TokenRefreshException extends BusinessException {

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message), ErrorCode.INVALID_TOKEN);
    }
}