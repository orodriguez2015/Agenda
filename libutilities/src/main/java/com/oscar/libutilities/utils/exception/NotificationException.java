package com.oscar.libutilities.utils.exception;

/**
 * Created by oscar on 11/02/17.
 */
public class NotificationException extends Exception {

    /**
     * Constructor
     */
    public NotificationException() {
        super();
    }

    /**
     * Constructor
     * @param message String
     */
    public NotificationException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param message String
     * @param t Throwable
     */
    public NotificationException(String message, Throwable t) {
        super(message,t);
    }

}
