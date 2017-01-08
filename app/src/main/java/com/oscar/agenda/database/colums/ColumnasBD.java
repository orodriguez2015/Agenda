package com.oscar.agenda.database.colums;

import android.provider.BaseColumns;

/**
 * Created by oscar on 05/11/16.
 */

public class ColumnasBD {


    /**
     * Definición de las columnas para la tabla Evento de la base de datos
     */
    public static abstract class AgendaEntry implements BaseColumns {

        public static final String TABLE_NAME           = "evento";
        public static final String NOMBRE               = "nombre";
        public static final String FECHA_DESDE          = "fecha_desde";
        public static final String HORA_DESDE           = "hora_desde";
        public static final String FECHA_HASTA          = "fecha_hasta";
        public static final String HORA_HASTA           = "hora_hasta";
        public static final String FECHA_PUBLICACION    = "fecha_publicacion";
        public static final String HORA_PUBLICACION     = "hora_publicacion";
        public static final String RECORDATORIO_1       = "recordatorio_1";
        public static final String RECORDATORIO_2       = "recordatorio_2";
        public static final String RECORDATORIO_3       = "recordatorio_3";
        public static final String ESTADO               = "estado";

    }


    /**
     * Definición de las columnas para la tabla Serie de la base de datos
     */
    public static abstract class SerieEntry implements BaseColumns {

        public static final String TABLE_NAME           ="serie";
        public static final String ID                   = "id";
        public static final String TITULO               = "titulo";
        public static final String DESCRIPCION          = "descripcion";
        public static final String FECHA_PUBLICACION    = "fechaPublicacion";
    }


    /**
     * Definición de las columnas para la tabla CapituloSerie de la base de datos
     */
    public static abstract class CapituloSerieEntry implements BaseColumns {

        public static final String TABLE_NAME           = "capitulo_serie";
        public static final String ID                   = "id";
        public static final String ID_SERIE             = "idSerie";
        public static final String TITULO               = "titulo";
        public static final String DESCRIPCION          = "descripcion";
        public static final String DESCRIPCION_COMPLETA = "descripcionCompleta";
        public static final String FECHA_PUBLICACION    = "fechaPublicacion";
    }


}




