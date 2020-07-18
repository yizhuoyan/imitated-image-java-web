package com.yizhuoyan.imitatedimage.exception;

public class SizeFormatErrorException extends RuntimeException {
    private String errorSize;


    public SizeFormatErrorException(String errorSize) {
        this.errorSize=errorSize;
    }

    public SizeFormatErrorException(String errorSize, Throwable cause) {
        super(cause);
        this.errorSize=errorSize;
    }

    public String getErrorSize(){
        return this.errorSize;
    }
}
