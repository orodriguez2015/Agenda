package com.oscar.agenda.database.entity;

import java.io.Serializable;

/**
 * Clase SerieVO
 * Created by oscar on 05/11/16.
 */
public class SerieVO implements Serializable {

    private Integer id               = null;
    private String titulo            = null;
    private String descripcion       = null;
    private String fechaPublicacion  = null;
    private String fechaModificacion = null;

    /**
     * Constructor
     */
    public SerieVO() {

    }

    /**
     * Constructor
     * @param id Integer
     * @param titulo String
     * @param descripcion String
     */
    public SerieVO(Integer id, String titulo, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el id de la serie
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
     * Devuelve el título de la serie
     * @return String
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la serie
     * @param titulo String
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Devuelve la descripción de la serie
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la serie
     * @param descripcion String
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la fecha de publicación de la serie
     * @return String
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece la fecha de publicación de la serie
     * @param fechaPublicacion String
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Devuelve la fecha de última modificación de la seri
     * @return String
     */
    public String getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * Establece la fecha de última modificación de la serie
     * @param fechaModificacion String
     */
    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
