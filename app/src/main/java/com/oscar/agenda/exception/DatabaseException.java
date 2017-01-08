package com.oscar.agenda.exception;

/**
 * Excepción que se lanza cuando se produce un error al realizar
 * una operación contra la base de datos
 * Created by oscar on 25/08/16.
 */
public class DatabaseException extends Exception {

    private Integer status;

    /**
     * Constructor
     * @param statusCode: Integer que representa el código del error que se ha producido
     * @param message: Mensaje de error
     */
    public DatabaseException(Integer statusCode, String message) {
        super(message);
        this.status = statusCode;
    }


    /**
     * Constructor
     * @param statusCode: Integer que representa el código del error que se ha producido
     * @param message: Mensaje de error
     * @param t: Excepción que ha sido lanzaza
     */
    public DatabaseException(Integer statusCode, String message, Throwable t) {
        super(message,t);
    }

    /**
     * Devuelve el código de error
     * @return Integer
     */
    public Integer getStatus() {
        return status;
    }
}
