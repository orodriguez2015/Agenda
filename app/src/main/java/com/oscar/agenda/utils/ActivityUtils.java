package com.oscar.agenda.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.oscar.agenda.dialog.AlertDialogHelper;
import com.oscar.agenda.dialog.DatePickerFragment;
import com.oscar.agenda.dialog.TimerPickerFragment;

/**
 * Clase ActivityUtils con operaciones que pueden ser utilizadas desde una activity
 * Created by oscar on 21/01/17.
 */
public class ActivityUtils {

    /**
     * Método que cierra el teclado en caso de estar abierto
     * @param activity Activity
     */
    public static void closeSoftKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    /**
     * Lanza el fragment que muestra un DatePickerDialog
     * @param activity Activity
     * @param textView TextView
     */
    public static void lanzarDatePickerFragment(Activity activity, TextView textView) {
        DatePickerFragment newFragment = new DatePickerFragment(textView);
        newFragment.show(activity.getFragmentManager(), "datePicker");
    }


    /**
     * Lanza el fragment que muestra un TimePickerFragment
     * @param activity Activity
     * @param textView TextView
     */
    public static void lanzarTimePickerFragment(Activity activity,TextView textView) {
        TimerPickerFragment newFragment = new TimerPickerFragment(textView);
        newFragment.show(activity.getFragmentManager(), "timePicker");
    }

    /**
     * Muestra un AlertDialog con un determinado mensaje
     * @param contexto Context
     * @param titulo String con el título
     * @param mensaje String con el mensaje
     */
    public static void mostrarMensajeAdvertencia(Context contexto,String titulo,String mensaje) {
        AlertDialogHelper.crearDialogoAlertaAdvertencia(contexto,titulo,mensaje).show();
    }

}
