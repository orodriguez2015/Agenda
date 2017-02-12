package com.oscar.agenda.utils.notification;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Clase NotificacionInfo con los datos necesarios para poder enviar una notificacion
 * Created by oscar on 12/02/17
 */
public class NotificacionInfo {

    @NonNull
    private String title;
    @NonNull
    private String descripcion;
    @NonNull
    private Context context;
    @NonNull
    private Intent intentDestino;
    @NonNull
    private Integer icon;


    /**
     * Constructor
     */
    public NotificacionInfo() {

    }

    /**
     * Constructor
     * @param title String
     * @param descripcion String
     * @param context Context
     * @param intentDestino Intent
     */
    public NotificacionInfo(@NonNull String title, @NonNull String descripcion, @NonNull Context context, @NonNull Intent intentDestino) {
        this.title = title;
        this.descripcion = descripcion;
        this.context = context;
        this.intentDestino = intentDestino;
    }


    /**
     * Devuelve el título de la notificación
     * @return String
     */
    @NonNull
    public String getTitle() {
        return title;
    }


    /**
     * Establece el título de la notificación
     * @param title String
     */
    public void setTitle(@NonNull String title) {
        this.title = title;
    }


    /**
     * Devuelve la descripción de la notificación
     * @return String
     */
    @NonNull
    public String getDescripcion() {
        return descripcion;
    }


    /**
     * Establece la descripión de la notificación
     * @param descripcion String
     */
    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Devuelve el Context
     * @return Context
     */
    @NonNull
    public Context getContext() {
        return context;
    }


    /**
     * Establece el Context
     * @return Context
     */
    public void setContext(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Devuelve el Intent al que se invocará cuando el usuario pulsa sobre la notificación
     * @return Intent
     */
    @NonNull
    public Intent getIntentDestino() {
        return intentDestino;
    }

    /**
     * Establece el Intent al que se invocará cuando el usuario pulsa sobre la notificación
     * @param intentDestino Intent
     */
    public void setIntentDestino(@NonNull Intent intentDestino) {
        this.intentDestino = intentDestino;
    }

    /**
     * Devuelve el id del icono a mostrar en la notificación
     * @return Integer
     */
    public Integer getIcon() {
        return icon;
    }

    /**
     * Establece el id del icono a mostrar en la notificación
     * @param icon Integer
     */
    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
