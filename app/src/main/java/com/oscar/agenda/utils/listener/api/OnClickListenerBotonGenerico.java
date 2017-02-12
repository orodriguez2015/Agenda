package com.oscar.agenda.utils.listener.api;

import android.content.DialogInterface;

import com.oscar.libutilities.utils.log.LogCat;


/**
 * Clase OnClickListenerBotonGenerico que se utiliza para detectar cuando se hace click
 * sobre un botón de un AlertDialog.
 *
 * El evento onClick no hace nada
 * Created by oscar on 12/11/16.
 */
public class OnClickListenerBotonGenerico implements DialogInterface.OnClickListener {

    public void onClick(DialogInterface dialog, int which) {
        LogCat.debug(" =======> Boton genérico ======>");
    }
}
