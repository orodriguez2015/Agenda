package com.oscar.agenda.database.asynctasks;

import android.content.Context;

import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.entity.SerieVO;

import java.io.Serializable;
import java.util.Calendar;


/**
 * Clase ParametrosAsyncTask utilizada para el paso de parámetros en aquellas
 * tareas AsyncTask para las que sea necesaria.
 *
 * Created by oscar on 26/08/16.
 */
public class ParamsAsyncTask implements Serializable {

    private Context context  = null;
    private SerieVO serie    = null;
    private EventoVO evento  = null;
    private Integer mes = null;
    private Calendar fecha = null;

    /**
     * Devuelve un mes
     * @return Integer
     */
    public Integer getMes() {
        return mes;
    }

    /**
     * Establece el mes
     * @param mes Integer
     */
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    /**
     * Devuelve un Evento

     * @return EventoVO
     */
    public EventoVO getEvento() {
        return evento;
    }


    /**
     * Establece un Evento
     * @param evento EventoVO
     */
    public void setEvento(EventoVO evento) {
        this.evento = evento;
    }

    /**
     * Devuelve el Context
     * @return Context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Establece el Context
     * @param context: Context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Devuelve una serie
     * @return SerieVO
     */
    public SerieVO getSerie() {
        return serie;
    }

    /**
     * Establece una serie
     * @param serie SerieVO
     */
    public void setSerie(SerieVO serie) {
        this.serie = serie;
    }


    /**
     * Devuelve la fecha
     * @return Calendar
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * Establecer fecha
     * @param fecha Calendar
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
}