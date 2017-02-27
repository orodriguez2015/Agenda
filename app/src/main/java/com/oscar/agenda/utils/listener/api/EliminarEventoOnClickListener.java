package com.oscar.agenda.utils.listener.api;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.oscar.agenda.database.asynctasks.DeleteEventoAsyncTask;
import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.libutilities.utils.dialog.AlertDialogHelper;

import agenda.oscar.com.agenda.R;

import static android.app.Activity.RESULT_OK;

/**
 * Listener asociado a un botónn y que permite eliminar un determinado evento de la base de datos
 * Created by oscar on 28/02/17.
 */
public class EliminarEventoOnClickListener implements DialogInterface.OnClickListener {

    private EventoVO evento;
    private Activity actividad;

    /**
     * Constructor
     * @param actividad Context
     * @param evento EventoVO
     */
    public EliminarEventoOnClickListener(Activity actividad,EventoVO evento) {
        this.actividad = actividad;
        this.evento  = evento;
    }


    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which The button that was clicked (e.g.
     *            {@link DialogInterface#BUTTON1}) or the position
     *            of the item clicked.
     */
    public void onClick(DialogInterface dialog, int which) {

        Context context = actividad.getApplicationContext();
        ParamsAsyncTask params = new ParamsAsyncTask();
        params.setContext(actividad.getApplicationContext());
        params.setEvento(evento);

        try {
            DeleteEventoAsyncTask task = new DeleteEventoAsyncTask();
            task.execute(params);

            ResponseAsyncTask res = task.get();
            if(res!=null) {

                switch(res.getStatus()) {
                    case 0: {
                        // Se devuelve un Intent al activity que envió la petición a la activity actual
                        Intent data = new Intent();
                        data.putExtra("fechaSeleccionada",evento.getFechaDesde());
                        actividad.setResult(RESULT_OK, data);
                        actividad.finish();
                        break;
                    }

                    case 1: {
                        AlertDialogHelper.crearDialogoAlertaAdvertencia(context,actividad.getString(R.string.error),actividad.getString(R.string.err_context_not_found));
                        break;
                    }

                    case 2: {
                        AlertDialogHelper.crearDialogoAlertaAdvertencia(context,actividad.getString(R.string.error),actividad.getString(R.string.err_unknown_event));
                        break;
                    }

                    case 3: {
                        AlertDialogHelper.crearDialogoAlertaAdvertencia(context,actividad.getString(R.string.error),actividad.getString(R.string.err_delete_evento));
                        break;
                    }
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }


    }

}
