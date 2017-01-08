package com.oscar.agenda.dialog;

import android.view.View;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by oscar on 29/11/16.
 */

public class ParamsDialog implements Serializable {

    private View objeto = null;
    private Calendar fecha = null;

    /**
     * Devuelve el objeto de tipo View
     * @return View
     */
    public View getObjeto() {
        return objeto;
    }

    /**
     * Establece el objeto de tipo View
     * @param objeto View
     */
    public void setObjeto(View objeto) {
        this.objeto = objeto;
    }

    /**
     * Devuelve el calendar
     * @return Calendar
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * Establece el calendar
     * @param fecha Calendar
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

}