package com.oscar.agenda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.oscar.agenda.database.asynctasks.GetEventosMesAsyncTask;
import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.dialog.AlertDialogHelper;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.List;

import agenda.oscar.com.agenda.R;

import static agenda.oscar.com.agenda.R.id.calendarView;

/**
 * Actividad que muestra el calendario mensual de eventos
 */
public class CalendarioMensualActivity extends AppCompatActivity {
    // Objeto que muestra el calendario
    private MaterialCalendarView materialCalendarView;
    private List<EventoVO> eventos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_mensual);

        materialCalendarView = (MaterialCalendarView)findViewById(calendarView);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        marcarEventosCalendario(1);
    }


    /**
     * Marca los días que tienen asociado algún evento
     * @param mes Integer
     */
    private void marcarEventosCalendario(Integer mes) {
        Calendar fechaActual = Calendar.getInstance();

        // Se recupera los eventos del mes actual de la base de datos
        ParamsAsyncTask params = new ParamsAsyncTask();
        params.setContext(getApplicationContext());
        params.setMes(1);

        try {
            GetEventosMesAsyncTask task = new GetEventosMesAsyncTask();
            task.execute(params);
            ResponseAsyncTask response = task.get();

            String message = null;
            switch(response.getStatus()) {

                case 0: {
                    // Se ha recuperado los eventos de la BBDD
                    eventos = response.getEventos();
                    marcarEventos();
                    break;
                }

                case 1: {
                    mostrarMensajeAdvertencia(getString(R.string.err_context_not_found));
                    break;
                }

                case 2: {
                    mostrarMensajeAdvertencia(getString(R.string.err_month_unknown));
                    break;
                }

                case 3: {
                    mostrarMensajeAdvertencia(getString(R.string.err_get_events_month));
                    break;
                }
            }// switch

        } catch(Exception e) {
            e.printStackTrace();

        }

        /*
        Calendar nuevo = Calendar.getInstance();
        nuevo.add(Calendar.DAY_OF_WEEK,2);

        materialCalendarView.setDateSelected(fechaActual,true);
        materialCalendarView.setDateSelected(nuevo,true);
        */
    }


    private void marcarEventos() {

        for(int i=0;eventos!=null && i<eventos.size();i++) {
            materialCalendarView.setDateSelected(eventos.get(i).getFechaDesdeCalendar(), true);
            //materialCalendarView.setSelectionColor(R.color.green);
        }

    }


    /**
     * Operación que muestra un mensaje de advertencia
     * @param mensaje String
     */
    private void mostrarMensajeAdvertencia(String mensaje) {
        AlertDialogHelper.crearDialogoAlertaAdvertencia(getApplicationContext(),getString(R.string.atencion),mensaje).show();
    }


    /**
     * Se sobreescribe el método onKeyDown para detectar que tecla ha pulsado el usuario, y
     * ejecutar la acción que corresponda. En este caso, si pulsa el botón atrás, se devuelve un intent
     * a la actividad MainActivity para que esta ejecute la acción que sea conveniente
     * @param keyCode int
     * @param event KeyEvent
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            volverAtras();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Comunica por medio deun Intent a la MainActivity que todo está OK.
     * A continuacion se vuelve hacia atrás. Se pasa el id de la noticia que se está visualizando, en el caso de que lo tenga
     */
    private void volverAtras() {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        onBackPressed();
    }

}
