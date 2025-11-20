package com.pub.exceptions;


public class TournoiException extends Exception {

    public TournoiException(String message) {
        super(message);
    }
    

    public TournoiException(String message, Throwable cause) {
        super(message, cause);
    }
}
