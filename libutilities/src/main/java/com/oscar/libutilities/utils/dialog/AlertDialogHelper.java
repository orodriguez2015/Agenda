package com.oscar.libutilities.utils.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import utilities.android.oscar.com.libutilities.R;


/**
 * Created by oscar on 27/08/16.
 */
public class AlertDialogHelper  {

    /**
     * Operación que crea un AlertDialog de Android simple con un determinado mensaje
     * @param activity: Actividad padre sobre la que se mostrará el AlertDialog
     * @param titulo: Título del activity
     * @param mensaje: Mensaje a mostrar al usuario
     * @param aceptar: Clase que implementa la interfaz DialogInterface.OnClickListener para la acción asociada
     *                 al botón de Aceptar
     * @param cancelar: Clase que implementa la interfaz DialogInterface.OnClickListener para la acción asociada
     *                 al botón de Cancelar
     * @return AlertDialog
     */
    public static AlertDialog crearDialogoAlertaConfirmacion(final Activity activity, String titulo, String mensaje, DialogInterface.OnClickListener aceptar, DialogInterface.OnClickListener cancelar) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        // Acción para el botón de "Aceptar"
        builder.setPositiveButton(activity.getString(R.string.botonAceptar),aceptar);
        // Acción para el botón de "Cancelar"
        builder.setNegativeButton(activity.getString(R.string.botonCancelar),cancelar);

        return builder.create();
    }


    /**
     * Operación que crea un AlertDialog de Android para mostrar únicamente un mensaje de advertencia al usuario.
     * Sólo muestra un botón de [Aceptar] al cual no se le puede asociar ningún listener
     * @param context Actividad padre sobre la que se mostrará el AlertDialog
     * @param titulo Título del activity
     * @param mensaje Mensaje a mostrar al usuario
     * @return AlertDialog
     */
    //public static AlertDialog crearDialogoAlertaAdvertencia(final Activity activity, String titulo, String mensaje) {
    public static AlertDialog crearDialogoAlertaAdvertencia(final Context context, String titulo, String mensaje) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        // Acción para el botón de "Aceptar"
        builder.setPositiveButton(context.getString(R.string.botonAceptar),new BtnAceptarCancelarDialogGenerico());

        return builder.create();
    }



    /**
     * Operación que crea un AlertDialog de Android simple con un determinado mensaje
     * @param activity: Actividad padre sobre la que se mostrará el AlertDialog
     * @param titulo: Título del activity
     * @param mensaje: Mensaje a mostrar al usuario
     * @param aceptar: Clase que implementa la interfaz DialogInterface.OnClickListener para la acción asociada
     *                 al botón de Aceptar
     * @return AlertDialog
     */
    public static AlertDialog crearDialogoAlertaSimple(final Activity activity, String titulo, String mensaje, DialogInterface.OnClickListener aceptar) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        // Acción para el botón de "Aceptar"
        builder.setPositiveButton(activity.getString(R.string.botonAceptar),aceptar);

        return builder.create();
    }

}