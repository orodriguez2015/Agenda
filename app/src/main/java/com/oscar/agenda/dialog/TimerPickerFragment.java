package com.oscar.agenda.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.oscar.libutilities.utils.date.DateOperations;

import java.util.Calendar;

/**
 * Clase que representa a un fragmento a partir del cual el usuario podrá seleccionar una hora
 * Created by oscar on 28/11/16.
 */
public class TimerPickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private View objeto = null;


    /**
     * Constructor
     */
    public TimerPickerFragment() {
    }


    /**
     * Constructor
     * @param objeto Objeto de la clase View que hace referencia cuyo contenido va a cambiar
     *               una vez que el usuario haya seleccionado una hora
     */
    @SuppressLint("ValidFragment")
    public TimerPickerFragment(View objeto) {
        this.objeto = objeto;
    }


    /**
     * Método onCreateDialog que crea el DatePickerDialog con la fecha de hoy seleccionada
     * @param savedInstanceState Bundle
     * @return Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        //int hora = c.get(Calendar.HOUR_OF_DAY);
        //int minutos = c.get(Calendar.MINUTE);
        int hora = 0;
        int minutos = 0;

        if(this.objeto!=null && !TextUtils.isEmpty(((TextView)this.objeto).getText().toString())) {
            String sHoraFinal = ((TextView)this.objeto).getText().toString();
            String[] datos = sHoraFinal.split(":");
            if(datos!=null && datos.length>=2) {
                hora    = Integer.parseInt(datos[0]);
                minutos = Integer.parseInt(datos[1]);
            }
         } else {
            hora    = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);
         }

        return new TimePickerDialog(getActivity(),this,hora,minutos,true);

    }


    /**
     * Método que es invocado cuando el usuario selecciona una hora en el TimePickerDialog
     * @param view the view associated with this listener
     * @param hourOfDay the hour that was set
     * @param minute the minute that was set
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(this.objeto!=null && this.objeto instanceof TextView) {
            ((TextView)objeto).setText(DateOperations.getHora(hourOfDay,minute));
        }
    }

}
