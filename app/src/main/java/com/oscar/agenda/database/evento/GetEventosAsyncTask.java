package com.oscar.agenda.database.evento;

import android.content.Context;
import android.os.AsyncTask;

import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.entity.SerieVO;
import com.oscar.agenda.database.helper.SerieDBHelper;
import com.oscar.agenda.exception.DatabaseException;

import java.util.Calendar;
import java.util.List;

import agenda.oscar.com.agenda.R;


/**
 * Tarea asíncrona a través de la cual se recuperan los eventos existentes en la base de datos
 * Created by oscar on 21/11/16
 */
public class GetEventosAsyncTask extends AsyncTask<ParamsAsyncTask,Void,ResponseAsyncTask> {

    /**
     * Metodo doInBackground que ejecuta la tarea asíncrona
     * @param params Objeto de tipo ParamsAsyncTask que contiene los parámetros necesarios para ejecutar la tarea
     * @return ResponseAsyncTask:
     *         ResponseAsyncTask.status = 0 => Se han recuperado los eventos
     *         ResponseAsyncTask.status = 1 => Contexto desconocido
     *         ResponseAsyncTask.status = 2 => Error al recuperar los eventos de bbdd
     */
    @Override
    protected ResponseAsyncTask doInBackground(final ParamsAsyncTask... params) {
        ResponseAsyncTask res = new ResponseAsyncTask();
        Context context       = params[0].getContext();
        Integer status        = -1;
        String descStatus     = null;
        List<SerieVO> series  = null;

        if(context==null) {
            res.setStatus(1);
            res.setDescStatus(context.getString(R.string.err_context_not_found));
        } else {

            try {
                SerieDBHelper helper = new SerieDBHelper(context);
                // Se recuperan los eventos del día de hoy
                res.setEventos(helper.getEventos(Calendar.getInstance()));
                res.setStatus(0);
                res.setDescStatus("OK");

            }catch(DatabaseException e) {
                res.setStatus(2);
                res.setDescStatus(context.getString(R.string.err_recuperar_eventos_hoy));
            }
        }

        return res;
    }


}
