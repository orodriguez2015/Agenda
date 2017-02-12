package com.oscar.libutilities.utils.dialog;

import android.content.DialogInterface;

import com.oscar.libutilities.utils.log.LogCat;

/**
 * Clase BtnAceptarDialogGenerico que se asocia a la acción de hacer click sobre un botón de un AlertDialog
 * @author oscar
 */
public class BtnAceptarCancelarDialogGenerico implements DialogInterface.OnClickListener {

    /**
     * onClick
     * @param var1: DialogInterface
     * @param var2:i nt
     */
    public void onClick(DialogInterface var1, int var2) {
        LogCat.debug("Ha pulsado el botón Aceptar");
    }

}
