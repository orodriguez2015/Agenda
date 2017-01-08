package com.oscar.agenda.database.helper;

/**
 * Códigos de error para las diferentes operaciones ejecutadas
 * contra la base de datos
 *
 * Created by oscar on 25/08/16.
 */
public class DatabaseErrors {

    public static final int OK                          = 0;
    public static final int ERROR_CREAR_BASEDATOS       = 1;
    public static final int ERROR_ACTUALIZAR_BASEDATOS  = 2;
    public static final int ERROR_RECUPERAR_SERIES      = 3;
    public static final int ERROR_INSERTAR_SERIE        = 4;
    public static final int ERROR_ELIMINAR_SERIE        = 5;
    public static final int ERROR_ACTUALIZAR_SERIE      = 6;
    public static final int ERROR_RECUPERAR_EVENTOS     = 7;
    public static final int ERROR_INSERTAR_EVENTO       = 8;
    public static final int ERROR_ELIMINAR_EVENTO       = 9;
    public static final int ERROR_RECUPERAR_EVENTOS_MES = 10;

}
