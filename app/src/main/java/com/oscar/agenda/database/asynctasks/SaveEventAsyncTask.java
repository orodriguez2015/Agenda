package com.oscar.agenda.database.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.helper.SerieDBHelper;
import com.oscar.agenda.exception.DatabaseException;

import agenda.oscar.com.agenda.R;


/**
 * Tarea asíncrona que graba un evento en base de datos
 * Created by oscar on 09/12/16.
 */
public class SaveEventAsyncTask extends AsyncTask<ParamsAsyncTask,Void,ResponseAsyncTask> {


    /**
     * Metodo doInBackground que ejecuta la tarea asíncrona
     * @param params Objeto de tipo ParamsAsyncTask que contiene los parámetros necesarios para ejecutar la tarea
     * @return ResponseAsyncTask:
     *         ResponseAsyncTask.status = 0 => Se han recuperado series
     *         ResponseAsyncTask.status = 1 => Contexto desconocido
     *         ResponseAsyncTask.status = 2 => Serie a grabar desconocida
     *         ResponseAsyncTask.status = 3 => Error al grabar la serie en base de datos
     */
    @Override
    protected ResponseAsyncTask doInBackground(final ParamsAsyncTask... params) {
        ResponseAsyncTask res = new ResponseAsyncTask();
        Context context       = params[0].getContext();
        EventoVO evento       = params[0].getEvento();

        if(context==null) {
            res.setStatus(1);
            res.setDescStatus(context.getString(R.string.err_context_not_found));
        } else
        if(evento==null) {
            res.setStatus(2);
            res.setDescStatus(context.getString(R.string.err_unknown_event));
        }
        else {

            try {
                SerieDBHelper helper = new SerieDBHelper(context);
                helper.saveEvento(evento);

                res.setStatus(0);
                res.setDescStatus("OK");

            }catch(DatabaseException e) {
                res.setStatus(3);
                res.setDescStatus(context.getString(R.string.err_grabar_evento_bbdd));
            }
        }
        return res;
    }

}
