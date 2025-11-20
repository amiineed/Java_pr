package com.pub.exceptions;


public class StockRuptureException extends Exception {
    

    public StockRuptureException(String message) {
        super(message);
    }
    

    public StockRuptureException(String message, Throwable cause) {
        super(message, cause);
    }
}
