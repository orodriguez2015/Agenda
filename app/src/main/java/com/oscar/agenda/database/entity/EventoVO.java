package com.oscar.agenda.database.entity;

import android.text.TextUtils;

import com.oscar.libutilities.utils.date.DateOperations;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Clase que representa un determinado evento de la agenda
 * Created by oscar on 19/11/16.
 */

public class EventoVO implements Serializable {

    private Integer id;
    private String nombre;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private String fechaPublicacion;
    private String horaPublicacion;
    private String recordatorio1;
    private String recordatorio2;
    private String recordatorio3;
    private Integer estado;

    /**
     * Devuelve el id
     * @return Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el id
     * @param id Integer
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del evento
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del evento
     * @param nombre String
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la fecha desde
     * @return String
     */
    public String getFechaDesde() {
        return fechaDesde;
    }

    /**
     * Establece la fecha desde del evento
     * @param fechaDesde String
     */
    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    /**
     * Devuelve la fecha hasta
     * @return String
     */
    public String getFechaHasta() {
        return fechaHasta;
    }

    /**
     * Establece la fecha hasta
     * @param fechaHasta String
     */
    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /**
     * Devuelve la fecha de publicación
     * @return String
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece la fecha de publicación
     * @param fechaPublicacion String
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Devuelve la fecha y hora del primer recordatorio
     * @return String
     */
    public String getRecordatorio1() {
        return recordatorio1;
    }

    /**
     * Establece la fecha y hora del primer recordatorio
     * @param recordatorio1 String
     */
    public void setRecordatorio1(String recordatorio1) {
        this.recordatorio1 = recordatorio1;
    }

    /**
     * Devuelve la fecha y hora del segundo recordatorio
     * @return String
     */
    public String getRecordatorio2() {
        return recordatorio2;
    }

    /**
     * Establece la fecha y hora del segundo recordatorio
     * @param recordatorio2 String
     */
    public void setRecordatorio2(String recordatorio2) {
        this.recordatorio2 = recordatorio2;
    }

    /**
     * Devuelve la fecha y hora del tercer recordatorio
     * @return String
     */
    public String getRecordatorio3() {
        return recordatorio3;
    }

    /**
     * Establece la fecha y hora del tercer recordatorio
     * @param recordatorio3 String
     */
    public void setRecordatorio3(String recordatorio3) {
        this.recordatorio3 = recordatorio3;
    }

    /**
     * Devuelve la hora desde del evento
     * @return String
     */
    public String getHoraDesde() {
        return horaDesde;
    }

    /**
     * Establece la hora desde del evento
     * @param horaDesde String
     */
    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    /**
     * Devuelve la hora hasta del evento
     * @return String
     */
    public String getHoraHasta() {
        return horaHasta;
    }

    /**
     * Establece la hora hasta del evento
     * @param horaHasta String
     */
    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    /**
     * Devuelve la hora de la publicación del evento
     * @return String
     */
    public String getHoraPublicacion() {
        return horaPublicacion;
    }

    /**
     * Establece la hora de la publicación del evento
     * @return String
     */
    public void setHoraPublicacion(String horaPublicacion) {
        this.horaPublicacion = horaPublicacion;
    }

    /**
     * Devuelve el estado del evento ( 0: Desactivado, 1: Activo)
     * @return Integer
     */
    public Integer getEstado() {
        return estado;
    }


    /**
     * Establece el estado del evento
     * @param estado Integer
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }


    /**
     * Devuelve la fecha desde en formato Calendar
     * @return Calendar
     */
    public Calendar getFechaDesdeCalendar() {
        Calendar calendar = null;
        if(!TextUtils.isEmpty(this.fechaDesde)) {
            calendar = DateOperations.getCalendar(this.fechaDesde);
        }
        return calendar;
    }


    /**
     * Devuelve la fecha hasta en formato Calendar
     * @return Calendar
     */
    public Calendar getFechaHastaCalendar() {
        Calendar calendar = null;
        if(!TextUtils.isEmpty(this.fechaHasta)) {
            calendar = DateOperations.getCalendar(this.fechaHasta);
        }

        return calendar;
    }
}
