package com.oscar.agenda.activities;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.utils.ActivityUtils;
import com.oscar.agenda.utils.Constantes;
import com.oscar.agenda.utils.LogCat;

import agenda.oscar.com.agenda.R;

import static com.oscar.agenda.utils.ActivityUtils.lanzarTimePickerFragment;

/**
 * Actividad a través de la cual se puede editar y consultar el detalle de un evento
 */
public class EdicionDetalleActivity extends AppCompatActivity {

    private Button botonGrabarEvento = null;
    private Button botonCancelarEvento = null;
    private EditText nombreEvento = null;
    private TextView fechaDesde   = null;
    private TextView horaDesde    = null;
    private TextView fechaHasta   = null;
    private TextView horaHasta    = null;
    private View view = null;


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

        // Se habilita el botón de atrás (En el AndroidManifest hay que poner la actividad a la que vuelve hacia
        // atrás android:parentActivityName="com.oscar.agenda.activities.WelcomeActivity")
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        ViewCompat.setTransitionName(findViewById(R.id.app_bar),"");
        nombreEvento        = (EditText)findViewById(R.id.nombreEvento);
        fechaDesde          = (TextView)findViewById(R.id.fechaDesde);
        horaDesde           = (TextView)findViewById(R.id.horaDesde);
        fechaHasta          = (TextView)findViewById(R.id.fechaHasta);
        horaHasta           = (TextView)findViewById(R.id.horaHasta);
        botonGrabarEvento   = (Button)findViewById(R.id.btnGrabarEvento);
        botonCancelarEvento = (Button)findViewById(R.id.btnCancelarEvento);

        cargarEvento();
        configurarListener();

    }

    /**
     * Método que se encarga de cargar el evento seleccionado por el usuario en el formulario de la activity
     */
    private void cargarEvento() {
        Bundle bundle   = getIntent().getExtras();
        EventoVO evento = (EventoVO)bundle.getSerializable(Constantes.PARAM_EVENTO_INTENT);

        if(!TextUtils.isEmpty(evento.getNombre())) {
            this.nombreEvento.setText(evento.getNombre());
        }

        if(!TextUtils.isEmpty(evento.getFechaDesde())) {
            this.fechaDesde.setText(evento.getFechaDesde());
        }

        if(!TextUtils.isEmpty(evento.getFechaHasta())) {
            this.fechaHasta.setText(evento.getFechaHasta());
        }

        if(!TextUtils.isEmpty(evento.getHoraDesde())) {
            this.horaDesde.setText(evento.getHoraDesde());
        }

        if(!TextUtils.isEmpty(evento.getHoraHasta())) {
            this.horaHasta.setText(evento.getHoraHasta());
        }



        LogCat.debug("nombre : " + evento.getNombre());
        LogCat.debug("fecha desde : " + evento.getFechaDesde());
        LogCat.debug("hora desde : " + evento.getHoraDesde());
    }


    /**
     * Método que se encarga de configurar los listener sobre los componentes del
     * formulario de edición de un evento
     */
    private void configurarListener() {

        /**
         * Evento onClick sobre el botón de grabar
         */
        botonGrabarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                //verificarFormulario();
            }
        });


        /**
         * Evento onClick sobre el botón de cancelar
         */
        botonCancelarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(EdicionDetalleActivity.this);
                finish();
            }
        });


        /**
         * Listener para el campo de texto "Fecha desde"
         */
        fechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(EdicionDetalleActivity.this);
                ActivityUtils.lanzarDatePickerFragment(EdicionDetalleActivity.this,fechaDesde);
            }
        });


        /**
         * Listener para ella etiqueta de texto en la que se muestra la hora desde que ha seleccionado el usuario
         */
        horaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(EdicionDetalleActivity.this);
                lanzarTimePickerFragment(EdicionDetalleActivity.this,horaDesde);
            }
        });

        /**
         * Listener para el campo de texto "Fecha hasta"
         */
        fechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(EdicionDetalleActivity.this);
                ActivityUtils.lanzarDatePickerFragment(EdicionDetalleActivity.this,fechaHasta);
            }
        });


        /**
         * Listener para ella etiqueta de texto en la que se muestra la hora hasta que ha seleccionado el usuario
         */
        horaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(EdicionDetalleActivity.this);
                ActivityUtils.lanzarTimePickerFragment(EdicionDetalleActivity.this,horaHasta);
            }
        });

    }



}
