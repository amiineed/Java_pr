package com.pub.exceptions;


public class InscriptionCloseException extends RuntimeException {
    

    public InscriptionCloseException(String message) {
        super(message);
    }
    

    public InscriptionCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
