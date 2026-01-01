package com.portfolio.bloom.error;

/**
 * Custom exception for application-specific errors using ErrorEnum.
 */
public class CommonException extends RuntimeException {
    
    private final ErrorEnum errorEnum;
    private String[] args;

    public CommonException(ErrorEnum errorEnum) {
        super(errorEnum.getMessageId());
        this.errorEnum = errorEnum;
    }

    public CommonException(ErrorEnum errorEnum, String... args) {
        super(errorEnum.getMessageId());
        this.errorEnum = errorEnum;
        this.args = args;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
