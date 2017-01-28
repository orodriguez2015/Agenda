package com.oscar.agenda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.oscar.agenda.adapter.EventosMensualesAdapter;
import com.oscar.agenda.database.asynctasks.GetEventosAsyncTask;
import com.oscar.agenda.database.asynctasks.GetEventosMesAsyncTask;
import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.helper.DatabaseErrors;
import com.oscar.agenda.decorator.DecoratorEventDay;
import com.oscar.agenda.dialog.AlertDialogHelper;
import com.oscar.agenda.utils.Constantes;
import com.oscar.agenda.utils.DateOperations;
import com.oscar.agenda.utils.LogCat;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import agenda.oscar.com.agenda.R;

import static agenda.oscar.com.agenda.R.id.calendarView;

/**
 * Actividad que muestra el calendario mensual de eventos
 * @author oscar
 */
public class CalendarioMensualActivity extends AppCompatActivity {
    // Objeto que muestra el calendario
    private MaterialCalendarView materialCalendarView;
    private List<EventoVO> eventos = null;
    private RecyclerView recyclerEvents = null;
    private RecyclerView.LayoutManager lManager = null;
    private EventosMensualesAdapter adapter = null;
    private TextView txtEventos = null;
    private Calendar fechaSeleccionada = null;

    /**
     * onCreate
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_mensual);

        materialCalendarView = (MaterialCalendarView)findViewById(calendarView);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        txtEventos = (TextView)findViewById(R.id.txtEventosDia);
        txtEventos.setText("");

        // Se habilita la flecha que permite volver a la actividad desde la que se invocó a ésta
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Se obtiene el RecyclerView en el que se mostrarán los eventos de una determinada fecha que ha sido
        // seleccionada por le usuario en el MaterialCalendarView
        recyclerEvents = (RecyclerView) findViewById(R.id.recyclerEvents);
        recyclerEvents.setHasFixedSize(true);

        // Se crea un LinearLayoutManager para el RecyclerView
        lManager = new LinearLayoutManager(this);
        recyclerEvents.setLayoutManager(lManager);

        // Adaptador de eventos
        adapter = new EventosMensualesAdapter(new ArrayList<EventoVO>());
        recyclerEvents.setAdapter(adapter);


        // Listener para detectar una selección de un elemento del adapter
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos =  recyclerEvents.getChildAdapterPosition(view);
                cargarDetalleEvento(adapter.getItems().get(pos));
            }
        });

        // Se marcan en el MaterialCalendarView los eventos existentes en el mes actual
        marcarEventosCalendario(DateOperations.getActualMonth());
        // Configuración de los listener
        configurarListener();

        addDecoradorMaterialCalendarView();
    }


    /**
     * Configura los listener sobre los componentes de la interfaz de usuario
     */
    private void configurarListener() {

        materialCalendarView.setSelectionColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
        // Se detecta si hay un cambio de fecha, hay que recuperar los eventos de ese día
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // Se cargan los eventos del día seleccionado
                CalendarioMensualActivity.this.fechaSeleccionada = DateOperations.convert(date);
                getEventos(CalendarioMensualActivity.this.fechaSeleccionada);
            }
        });

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                LogCat.debug("Se ha cambiado de mes "  + date.getMonth());
            }
        });
    }


    private void addDecoradorMaterialCalendarView() {
        materialCalendarView.addDecorator(new DecoratorEventDay(getApplicationContext(),this.eventos));
    }


    /**
     * Carga la actividad que muestra el detalle de un evento. Esta actividad
     * también permite la edición del mismo
     * @param evento EventoVO
     */
    private void cargarDetalleEvento(EventoVO evento) {
        Intent intent = new Intent(CalendarioMensualActivity.this,EdicionDetalleActivity.class);
        intent.putExtra("evento",evento);
        startActivityForResult(intent,Constantes.EDITAR_EVENTO);
    }


    /**
     * Recupera los eventos de una determinada fecha
     * @param fecha
     */
    private void getEventos(Calendar fecha) {

        ParamsAsyncTask params = new ParamsAsyncTask();
        params.setFecha(fecha);
        params.setContext(getApplicationContext());
        List<EventoVO> eventos = null;

        try {
            GetEventosAsyncTask task = new GetEventosAsyncTask();
            task.execute(params);

            ResponseAsyncTask res  = task.get();
            if(res.getStatus().equals(DatabaseErrors.OK)) {
                eventos = res.getEventos();

                // Mensaje a mostrar en el caso de existir eventos
                String mensaje = getString(R.string.noHayEventosDia) + " " + DateOperations.getFecha(fecha,DateOperations.FORMATO.DIA_MES_ANYO);
                if(eventos.size()>0) {
                    mensaje = getString(R.string.eventosDia) + " " + DateOperations.getFecha(fecha,DateOperations.FORMATO.DIA_MES_ANYO);
                }
                txtEventos.setText(mensaje);

                // Se pasan los eventos al adapter
                adapter.setItems(eventos);
                adapter.notifyDataSetChanged();
                recyclerEvents.setAdapter(adapter);

            } else {
                AlertDialogHelper.crearDialogoAlertaAdvertencia(getApplicationContext(),getString(R.string.atencion),getString(R.string.err_get_eventos_fechaactual)).show();
            }

        } catch(Exception e) {
            LogCat.error("Error al recuperar los eventos de la fecha seleccionada: " + e.getMessage());
            //AlertDialogHelper.crearDialogoAlertaAdvertencia(getApplicationContext(),getString(R.string.atencion),getString(R.string.err_get_eventos_fechaactual)).show();
        }
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
        params.setMes(mes);

        try {
            GetEventosMesAsyncTask task = new GetEventosMesAsyncTask();
            task.execute(params);
            ResponseAsyncTask response = task.get();

            String message = null;
            switch(response.getStatus()) {

                case 0: {
                    // Se ha recuperado los eventos de la BBDD
                    eventos = response.getEventos();
                    //marcarEventosCalendario();
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
            mostrarMensajeAdvertencia(getString(R.string.err_get_events_month));
        }
    }


    /**
     * Se marca en el MaterialCalendarView los días en los que hay eventos
     */
    private void marcarEventosCalendario() {
        for(int i=0;eventos!=null && i<eventos.size();i++) {
            materialCalendarView.setDateSelected(eventos.get(i).getFechaDesdeCalendar(), true);
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


    /**
     * Método que es invocado cuando un activity secundaria invocada a través de un Intent, devuelve una respuesta
     * a esta activity
     * @param requestCode int
     * @param resultCode int
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {

            switch (requestCode) {
                case Constantes.EDITAR_EVENTO: {
                    // Recargar los eventos del día seleccionado por el usuario
                    getEventos(this.fechaSeleccionada);
                    break;
                }

            }// switch
        }
    }



}
