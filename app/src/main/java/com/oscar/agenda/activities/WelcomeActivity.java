package com.oscar.agenda.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.oscar.agenda.services.NotificacionEventoService;
import com.oscar.agenda.utils.Constantes;
import com.oscar.agenda.utils.date.CalendarDayOperations;
import com.oscar.libutilities.utils.date.DateOperations;
import com.oscar.libutilities.utils.dialog.AlertDialogHelper;
import com.oscar.libutilities.utils.log.LogCat;
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
 * Actividad principal de la aplicación
 */
public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recycler = null;
    private RecyclerView.LayoutManager lManager = null;
    private List<EventoVO> eventos = null;
    private NavigationView navigationView = null;

    private MaterialCalendarView materialCalendarView = null;
    private Calendar fechaSeleccionada = null;
    private EventosMensualesAdapter adapter = null;
    private RecyclerView recyclerEvents = null;
    private TextView txtEventos = null;

    /**
     * Método onCreate
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_welcome);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        // NavigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        materialCalendarView = (MaterialCalendarView)findViewById(calendarView);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

        txtEventos = (TextView)findViewById(R.id.txtEventosDia);
        txtEventos.setText("");


        // Se obtiene el RecyclerView en el que se mostrarán los eventos de una determinada fecha que ha sido
        // seleccionada por le usuario en el MaterialCalendarView
        recyclerEvents = (RecyclerView) findViewById(R.id.recyclerListadoEventos);
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


        /**
         * Botón flotante
         */
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floatingButtonNuevoEvento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarActividadNuevoEvento();
            }
        });

        /**
         * Se lanza el servicio de notificaciones
         */
        lanzarServicio();

        /**
         * Se marcan en el MaterialCalendarView los eventos existentes en el mes actual
         */
        marcarEventosCalendario(DateOperations.getActualMonth());

        /**
         * Configuración de los listener del MaterialCalendarView
         */
        configurarListener();

        getEventos(Calendar.getInstance());

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
                WelcomeActivity.this.fechaSeleccionada = CalendarDayOperations.convert(date);

                try {
                    getEventos(WelcomeActivity.this.fechaSeleccionada);

                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                LogCat.debug("Se ha cambiado de mes "  + date.getMonth());
            }
        });
    }


    /**
     * Añade un decorador al MaterialCalendarView para marcar con un punto los días del calendario
     * que tienen asociado un evento
     */
    private void addDecoradorMaterialCalendarView() {
        materialCalendarView.addDecorator(new DecoratorEventDay(getApplicationContext(),this.eventos));
    }


    /**
     * Carga la actividad que muestra el detalle de un evento. Esta actividad
     * también permite la edición del mismo
     * @param evento EventoVO
     */
    private void cargarDetalleEvento(EventoVO evento) {
        Intent intent = new Intent(WelcomeActivity.this,EdicionDetalleActivity.class);
        intent.putExtra("evento",evento);
        startActivityForResult(intent,Constantes.EDITAR_EVENTO);
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

                    /**
                     * Se recuperan los eventos del mes de la BBDD
                     */
                    eventos = response.getEventos();

                    /**
                     * Añadir decorador para los días que tienen asociado algún evento
                     */
                    addDecoradorMaterialCalendarView();
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
     * Operación que muestra un mensaje de advertencia
     * @param mensaje String
     */
    private void mostrarMensajeAdvertencia(String mensaje) {
        AlertDialogHelper.crearDialogoAlertaAdvertencia(getApplicationContext(),getString(R.string.atencion),mensaje).show();
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
            e.printStackTrace();
        }
    }



    /**
     * Lanza el servicio de notificaciones una vez cargada la actividad
     */
    private void lanzarServicio() {
        startService(new Intent(this,NotificacionEventoService.class));
    }


    /**
     * onBackPressed
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_welcome);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
     * onCreateOptionsMenu
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_welcome_drawer, menu);
        return true;
    }


    /**
     * onOptionsItemSelected
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * onNavigationItemSelected
     * @param item MenuItem
     * @return boolean
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_calendarioMes: {
                lanzarActividadCalendarioMensual();
                //lanzarActividadEditar();
                break;
            }

            case R.id.nav_nuevoEvento: {
                lanzarActividadNuevoEvento();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_welcome);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Método que es ejecutado cuando el usuario pretender crear un nuevo
     * evento
     */
    private void lanzarActividadNuevoEvento() {
        Intent intent = new Intent(WelcomeActivity.this,NuevoEvento2Activity.class);
        startActivityForResult(intent, Constantes.NUEVO_EVENTO);
    }


    private void lanzarActividadCalendarioMensual() {
        LogCat.debug("lanzarActividadCalendarioMensual ====>");
        Intent intent = new Intent(WelcomeActivity.this,CalendarioMensualActivity.class);
        startActivity(intent);
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

                case Constantes.NUEVO_EVENTO: {
                    // Recargar el calendario para marcar los días que tienen eventos
                    marcarEventosCalendario(DateOperations.getActualMonth());

                    // Si con anterioridad el usuario ha seleccionado una fecha, entonces, hay que recargar los eventos de dicha fecha, por si el nuevo
                    // evento está asociado a la misma
                    if(this.fechaSeleccionada!=null) {
                        getEventos(this.fechaSeleccionada);
                    }


                    break;
                }

            }// switch
        }
    }

}
