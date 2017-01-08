package com.oscar.agenda.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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

import com.oscar.agenda.adapter.EventosAdapter;
import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.evento.GetEventosAsyncTask;
import com.oscar.agenda.dialog.AlertDialogHelper;
import com.oscar.agenda.utils.Constantes;
import com.oscar.agenda.utils.LogCat;
import com.oscar.agenda.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

import agenda.oscar.com.agenda.R;

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recycler = null;
    private RecyclerView.LayoutManager lManager = null;
    private EventosAdapter adapter = null;
    private List<EventoVO> eventos = null;
    private NavigationView navigationView = null;

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

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Adaptador de eventos
        adapter = new EventosAdapter(new ArrayList<EventoVO>(),getResources(),this);
        recycler.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.floatingButtonNuevoEvento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarActividadNuevoEvento();
            }
        });


        cargarEventosHoy();
    }


    /**
     * Carga en el Activity los eventos recuperados del día de hoy
     */
    public void cargarEventosHoy() {

        ParamsAsyncTask params = new ParamsAsyncTask();
        params.setContext(getApplicationContext());

        try {
            GetEventosAsyncTask task = new GetEventosAsyncTask();
            task.execute(params);
            ResponseAsyncTask res = task.get();

            switch(res.getStatus()) {
                case 0: {
                    eventos = res.getEventos();

                    for(int i=0;eventos!=null && i<eventos.size();i++) {
                        LogCat.debug("===> Nombre: " + eventos.get(i).getNombre());
                        LogCat.debug("===> Fecha desde: " + eventos.get(i).getFechaDesde());
                        LogCat.debug("===> Hora desde: " + eventos.get(i).getHoraDesde());
                        LogCat.debug("===> Fecha hasta: " + eventos.get(i).getFechaHasta());
                        LogCat.debug("===> Hora hasta: " + eventos.get(i).getHoraHasta());
                        LogCat.debug("===> Estado: " + eventos.get(i).getEstado());
                        LogCat.debug("");
                    }


                    adapter = new EventosAdapter(eventos,getResources(),this);
                    // Se notifica que ha cambiado  el contenido del adapter
                    adapter.notifyDataSetChanged();
                    recycler.setAdapter(adapter);
                    LogCat.debug("Número de eventos: " + eventos.size());
                    break;
                }

                case 1: {
                    LogCat.debug("Contexto desconocido");
                    MessageUtils.showToastDuracionCorta(getApplicationContext(),getString(R.string.err_context_not_found));
                }

                case 2: {
                    LogCat.debug("Error al recuperar los eventos del dia actual de base de datos");
                    MessageUtils.showToastDuracionCorta(getApplicationContext(),getString(R.string.err_recuperar_eventos_hoy));
                }

            }


        } catch(Exception e) {
            AlertDialogHelper.crearDialogoAlertaAdvertencia(getApplicationContext(),getString(R.string.atencion),getString(R.string.err_recuperar_eventos_hoy)).show();
        }

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
                    // Recargar las eventos para el día de hoy
                    this.cargarEventosHoy();
                    break;
                }

            }// switch
        }
    }

}


/*
public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

*/