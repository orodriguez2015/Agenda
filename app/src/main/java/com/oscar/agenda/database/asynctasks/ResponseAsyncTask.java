package com.oscar.agenda.database.asynctasks;

import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.entity.SerieVO;

import java.io.Serializable;
import java.util.List;

/**
 * Clase ResponseAsyncTask a través de la cual se devuelve la salida de las tareas asíncronas
 * Created by oscar on 05/11/16.
 */

public class ResponseAsyncTask implements Serializable {
    private Integer status    = null;
    private String descStatus = null;
    private List<EventoVO> eventos = null;
    private SerieVO serie = null;

    /**
     * Constructor
     */
    public ResponseAsyncTask() {
    }

    /**
     * Constructor
     * @param status: Integer
     * @param descStatus: String
     */
    public ResponseAsyncTask(Integer status, String descStatus) {
        this.status     = status;
        this.descStatus = descStatus;
    }

    /**
     * Devuelve la respuesta devuelta por el AsyncTask
     * @return Integer
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Establece la respuesta devuelta por el AsyncTask
     * @param status: Integer
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Devuelve la descripción del código de error
     * @return String
     */
    public String getDescStatus() {
        return descStatus;
    }

    /**
     * Permite establecer la descripción del código de error
     * @param descStatus: String
     */
    public void setDescStatus(String descStatus) {
        this.descStatus = descStatus;
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
     * @return SerieVO
     */
    public void setSerie(SerieVO serie) {
        this.serie = serie;
    }

    /**
     * Devuelve la lista de eventos
     * @return List<EventoVO>
     */
    public List<EventoVO> getEventos() {
        return eventos;
    }

    /**
     * Establece la lista de eventos
     * @param eventos List<EventoVO>
     */
    public void setEventos(List<EventoVO> eventos) {
        this.eventos = eventos;
    }

}
