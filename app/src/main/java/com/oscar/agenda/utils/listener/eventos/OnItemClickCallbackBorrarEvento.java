package com.oscar.agenda.utils.listener.eventos;

import android.content.DialogInterface;
import android.view.View;

import com.oscar.agenda.activities.WelcomeActivity;
import com.oscar.agenda.database.asynctasks.DeleteEventoAsyncTask;
import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.dialog.AlertDialogHelper;
import com.oscar.agenda.utils.listener.api.OnClickListenerBotonGenerico;
import com.oscar.agenda.utils.listener.api.OnItemClickListener;

import java.util.List;

import agenda.oscar.com.agenda.R;

/**
 * Clase que implementa la interfaz OnItemClickCallback
 * Created by oscar on 18/12/16.
 */

public class OnItemClickCallbackBorrarEvento implements OnItemClickListener.OnItemClickCallback {

    private List<EventoVO> eventos = null;
    private WelcomeActivity actividad = null;

    /**
     * Constructor
     * @param eventos List<EventoVO>
     * @param actividad Activity desde el que llega la petición de edición
     */
    public OnItemClickCallbackBorrarEvento(List<EventoVO> eventos, WelcomeActivity actividad) {
        this.eventos     = eventos;
        this.actividad  = actividad;
    }


    /**
     * onItemClicked
     * @param view View
     * @param position posición del item del RecyclerView seleccionado por el usuario
     */
    @Override
    public void onItemClicked(View view, final int position) {

        String titulo       = this.actividad.getString(R.string.atencion);
        String pregunta     = this.actividad.getString(R.string.pregunta_eliminar_evento) + " " + this.eventos.get(position).getNombre() + "?";

        AlertDialogHelper.crearDialogoAlertaConfirmacion(this.actividad,titulo,pregunta,

                /** Listener par el botón aceptar **/
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            // Se borra el evento de base de datos
                            ParamsAsyncTask params = new ParamsAsyncTask();
                            params.setContext(actividad.getApplicationContext());
                            params.setEvento(eventos.get(position));

                            DeleteEventoAsyncTask task = new DeleteEventoAsyncTask();
                            task.execute(params);
                            ResponseAsyncTask res =  task.get();

                            if(res.getStatus()==0) {
                                // Si se ha eliminado ,hay que actualizar el contenido de la actividad principal
                               actividad.cargarEventosHoy();
                            } else {
                                AlertDialogHelper.crearDialogoAlertaAdvertencia(actividad,actividad.getString(R.string.atencion),actividad.getString(R.string.err_delete_evento));
                            }

                        } catch(Exception e) {
                            AlertDialogHelper.crearDialogoAlertaAdvertencia(actividad,actividad.getString(R.string.atencion),actividad.getString(R.string.err_delete_evento));

                        }
                    }
                },
                /** Listener para el botón cancelar **/
                new OnClickListenerBotonGenerico()).show();
    }
}
