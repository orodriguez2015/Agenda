package com.oscar.agenda.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.utils.LogCat;

import agenda.oscar.com.agenda.R;

/**
 * Actividad a través de la cual se puede editar y consultar el detalle de un evento
 */
public class EdicionDetalleActivity extends AppCompatActivity {

    /**
     * onCreate
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_detalle);
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

        cargarEvento();
    }


    private void cargarEvento() {
        Bundle bundle = getIntent().getExtras();
        EventoVO evento = (EventoVO)bundle.getSerializable("evento");


        LogCat.debug("nombre : " + evento.getNombre());
        LogCat.debug("fecha desde : " + evento.getFechaDesde());
        LogCat.debug("hora desde : " + evento.getHoraDesde());
    }


}
