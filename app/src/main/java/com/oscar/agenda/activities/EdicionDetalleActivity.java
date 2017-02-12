package com.oscar.agenda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oscar.agenda.database.asynctasks.EditEventAsyncTask;
import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.utils.ActivityUtils;
import com.oscar.agenda.utils.Constantes;
import com.oscar.libutilities.utils.log.LogCat;
import com.oscar.libutilities.utils.date.DateOperations;

import java.util.Calendar;

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
    private EventoVO evento = null;


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
     * Método que se encarga de cargar el evento seleccionado por el usuario en el formulario de la activity.
     * Se envía en un Intent un objeto EventoVO cuyo nombre está definido en Constantes.PARAM_EVENTO_INTENT
     */
    private void cargarEvento() {
        Bundle bundle   = getIntent().getExtras();
        evento = (EventoVO)bundle.getSerializable(Constantes.PARAM_EVENTO_INTENT);

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
                verificarFormulario();
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



    /**
     * Verifica los campos que los campos obligatorios del formulario estén cubiertos para
     * poder proceder a realizar la edición del evento en la base de datos
     */
    private void verificarFormulario() {
        View focusableError = null;
        String nombre      = nombreEvento.getText().toString();
        String sFechaDesde = fechaDesde.getText().toString();
        String sHoraDesde  = horaDesde.getText().toString();
        String sFechaHasta = fechaHasta.getText().toString();
        String sHoraHasta  = horaHasta.getText().toString();

        if(TextUtils.isEmpty(nombre)) {
            nombreEvento.setError(getString(R.string.nombreEventoObligatorio));
            focusableError = nombreEvento;
        } else
        if(TextUtils.isEmpty(sFechaDesde)) {
            fechaDesde.setError(getString(R.string.fechaDesdeObligatorio));
            focusableError = fechaDesde;
        } else
        if(TextUtils.isEmpty(sHoraDesde)) {
            horaDesde.setError(getString(R.string.horaDesdeObligatorio));
            focusableError = horaDesde;
        } if(TextUtils.isEmpty(sFechaHasta)) {
            fechaDesde.setError(getString(R.string.fechaHastaObligatorio));
            focusableError = fechaDesde;
        } else
        if(TextUtils.isEmpty(sHoraHasta)) {
            horaDesde.setError(getString(R.string.horaHastaObligatorio));
            focusableError = horaDesde;
        }


        if(focusableError!=null) {
            focusableError.requestFocus();
        } else {
            // Se comprueba que la fecha desde sea <= que la fecha hasta

            String fd = sFechaDesde.concat(" ").concat(sHoraDesde);
            String fh = sFechaHasta.concat(" ").concat(sHoraHasta);

            Calendar desde = DateOperations.stringToCalendar(fd);
            Calendar hasta = DateOperations.stringToCalendar(fh);

            if(desde.getTimeInMillis()>hasta.getTimeInMillis()) {
                ActivityUtils.mostrarMensajeAdvertencia(getApplicationContext(),getString(R.string.atencion),getString(R.string.err_fecha_hasta_anterior_desde));

            } else {

                try {
                    // Se procede a dar de alta el evento en base de datos
                    EventoVO evento = new EventoVO();
                    evento.setId(this.evento.getId());
                    evento.setNombre(nombre);
                    evento.setEstado(1);
                    evento.setFechaPublicacion(DateOperations.getFecha(Calendar.getInstance(), DateOperations.FORMATO.DIA_MES_ANYO_HORA_MINUTOS_SEGUNDOS));
                    evento.setFechaDesde(sFechaDesde);
                    evento.setHoraDesde(sHoraDesde);
                    evento.setFechaHasta(sFechaHasta);
                    evento.setHoraHasta(sHoraHasta);

                    ParamsAsyncTask params = new ParamsAsyncTask();
                    params.setContext(getApplicationContext());
                    params.setEvento(evento);

                    EditEventAsyncTask task = new EditEventAsyncTask();
                    task.execute(params);

                    ResponseAsyncTask res = task.get();
                    if(res.getStatus()==0) {
                        // Se devuelve un Intent de OK al activity que ha enviado la petición a esta actividad
                        Intent data = new Intent();
                        setResult(RESULT_OK, data);
                        finish();
                    } else {
                        ActivityUtils.mostrarMensajeAdvertencia(getApplicationContext(),getString(R.string.atencion),getString(R.string.err_editar_evento_bbdd));
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }



}
