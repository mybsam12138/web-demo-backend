package com.demo.exception;

import lombok.Data;
import lombok.Getter;

@Data
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        this("SERVICE_ERROR", message);
    }
}
