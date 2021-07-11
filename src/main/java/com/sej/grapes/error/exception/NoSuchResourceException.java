package com.sej.grapes.error.exception;

import com.sej.grapes.error.ErrorCode;
import lombok.NoArgsConstructor;

public class NoSuchResourceException extends BusinessException {

    public NoSuchResourceException(String message) {
        super(message, ErrorCode.REQUEST_RESOURCE_NOT_EXIST);
    }
}
