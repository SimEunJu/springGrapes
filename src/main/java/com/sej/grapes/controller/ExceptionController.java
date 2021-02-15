package com.sej.grapes.controller;

import com.sej.grapes.error.ErrorCode;
import com.sej.grapes.error.ErrorResponse;
import com.sej.grapes.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    // @Valid 실패 시
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, e.getFieldErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @RequestBody에서 parameter object로 convert 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException : "+e.getMessage(), e);
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // @RequestMapping에 해당되는 메소드 없을 때
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("HttpRequestMethodNotSupportedException", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.MISSING_REQUIRED_INPUT_VALUE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 운영 시 발생하는 오류
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e){
        log.error("BusinessException", e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    // 기타 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        log.error("Exception", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
