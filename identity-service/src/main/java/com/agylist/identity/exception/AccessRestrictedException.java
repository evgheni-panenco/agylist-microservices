package com.agylist.identity.exception;

public class AccessRestrictedException extends RuntimeException {

    public AccessRestrictedException(String message) {
        super(message);
    }
}
