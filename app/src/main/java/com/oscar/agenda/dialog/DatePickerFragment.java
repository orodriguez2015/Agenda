package com.oscar.agenda.dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.oscar.libutilities.utils.log.LogCat;
import com.oscar.libutilities.utils.date.DateOperations;

import java.util.Calendar;

/**
 * Clase DatePickkerFragment que muestra un DatePickerDialog para que el usuario pueda seleccionar la fecha.
 * Si se le pasa un objeto
 * Created by oscar on 27/11/16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /** Objeto de tipo View asociado al DatePickerDialog **/
     private View objeto = null;

    /**
     * Constructor
     */
    public DatePickerFragment(){
    }

    /**
     * Constructor
     * @param objeto Objeto de tipo View asociado a la vista, que se usa para actualizar algún View una vez que el
     *               usuario ha seleccionado una fecha
     */
    @SuppressLint("ValidFragment")
    public DatePickerFragment(View objeto){
        this.objeto = objeto;
    }

    /**
     * Método onCreateDialog que crea el DatePickerDialog con la fecha de hoy seleccionada
     * @param savedInstanceState Bundle
     * @return Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year  = 0;
        int month = 0;
        int day   = 0;

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        if(this.objeto!=null && !TextUtils.isEmpty(((TextView)this.objeto).getText().toString())) {
            // Si hay una vista asociada, se obtiene la fecha del mismo y se muestra en el DatePickerDialog
            LogCat.debug(" Fecha que contiene el objeto: " + ((TextView)this.objeto).getText().toString());

            c = DateOperations.stringToCalendar(((TextView)this.objeto).getText().toString());
            if(c!=null) {
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }
        }

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    /**
     * Método que es ejecutado cuando el usuario selecciona una fecha
     * @param view DatePickerView que lanza el evento
     * @param year Año
     * @param month Mes (0..11)
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {

        if(this.objeto!=null && this.objeto instanceof TextView) {
            String fecha = DateOperations.getFecha(DateOperations.getCalendar(day,month,year), DateOperations.FORMATO.DIA_MES_ANYO);
            ((TextView)this.objeto).setText(fecha);
        }
    }
}