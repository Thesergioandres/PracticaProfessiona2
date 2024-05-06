package com.example.clasesabado.exceptions;

public class CustomIllegalArgumentException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomIllegalArgumentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

