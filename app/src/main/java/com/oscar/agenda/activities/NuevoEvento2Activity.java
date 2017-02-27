package com.oscar.agenda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oscar.agenda.database.asynctasks.ParamsAsyncTask;
import com.oscar.agenda.database.asynctasks.ResponseAsyncTask;
import com.oscar.agenda.database.asynctasks.SaveEventAsyncTask;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.utils.ActivityUtils;
import com.oscar.libutilities.utils.log.LogCat;
import com.oscar.libutilities.utils.date.DateOperations;
import com.oscar.libutilities.utils.dialog.AlertDialogHelper;

import java.util.Calendar;

import agenda.oscar.com.agenda.R;

/**
 * Activity NuevoEvento2Activity que muestra la pantalla de alta de un nuevo evento en la agenda
 * @author oscar
 */
public class NuevoEvento2Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_nuevo_evento2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNuevoEvento);
        setSupportActionBar(toolbar);

        // Se habilita el botón de atrás (En el AndroidManifest hay que poner la actividad a la que vuelve hacia
        // atrás android:parentActivityName="com.oscar.agenda.activities.WelcomeActivity")
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        // Se obtiene el AppBarLayout para detectar cuando está "collapsed"
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                LogCat.debug(" =============> verticalOffset:: " + verticalOffset);

                if (verticalOffset == 0) {
                    // AppBarLayout extendida
                    collapsingToolbarLayout.setTitle("");
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    // AppBarLayout colapsada
                    collapsingToolbarLayout.setTitle(getString(R.string.eventoNuevo));
                }
            }
        });


        ViewCompat.setTransitionName(findViewById(R.id.app_bar),"");

        nombreEvento        = (EditText)findViewById(R.id.nombreEvento);
        fechaDesde          = (TextView)findViewById(R.id.fechaDesde);
        horaDesde           = (TextView)findViewById(R.id.horaDesde);
        fechaHasta          = (TextView)findViewById(R.id.fechaHasta);
        horaHasta           = (TextView)findViewById(R.id.horaHasta);
        botonGrabarEvento   = (Button)findViewById(R.id.btnGrabarEvento);
        botonCancelarEvento = (Button)findViewById(R.id.btnCancelarEvento);

        // Se cargan los datos por defecto en el formulario de alta de evento
        cargarFormularioDefecto();
        // Configurar los listener sobre los componentes de la vista de alta de evento
        configurarListener();
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
                // closeSoftKeyBoard();
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
                ActivityUtils.closeSoftKeyBoard(NuevoEvento2Activity.this);
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
                ActivityUtils.closeSoftKeyBoard(NuevoEvento2Activity.this);
                ActivityUtils.lanzarDatePickerFragment(NuevoEvento2Activity.this,fechaDesde);
            }
        });


        /**
         * Listener para ella etiqueta de texto en la que se muestra la hora desde que ha seleccionado el usuario
         */
        horaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(NuevoEvento2Activity.this);
                ActivityUtils.lanzarTimePickerFragment(NuevoEvento2Activity.this,horaDesde);
            }
        });

        /**
         * Listener para el campo de texto "Fecha hasta"
         */
        fechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(NuevoEvento2Activity.this);
                ActivityUtils.lanzarDatePickerFragment(NuevoEvento2Activity.this,fechaHasta);
            }
        });


        /**
         * Listener para ella etiqueta de texto en la que se muestra la hora hasta que ha seleccionado el usuario
         */
        horaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el teclado
                ActivityUtils.closeSoftKeyBoard(NuevoEvento2Activity.this);
                ActivityUtils.lanzarTimePickerFragment(NuevoEvento2Activity.this,horaHasta);
            }
        });
    }



    /**
     * Carga los datos por defecto del formulario
     */
    private void cargarFormularioDefecto() {

        // Se obtiene la fecha y hora desde
        Calendar c = Calendar.getInstance();
        String fechaActual = DateOperations.getFecha(c,DateOperations.FORMATO.DIA_MES_ANYO);
        String horaActual  = DateOperations.getFecha(c,DateOperations.FORMATO.HORA_MINUTOS);

        fechaDesde.setText(fechaActual);
        horaDesde.setText(horaActual);

        // Se obtiene la fecha y hora hasta
        Calendar cHasta = DateOperations.addHourToCalendar(c,1);
        String sfechaHasta = DateOperations.getFecha(cHasta,DateOperations.FORMATO.DIA_MES_ANYO);
        String shoraHasta = DateOperations.getFecha(cHasta,DateOperations.FORMATO.HORA_MINUTOS);

        fechaHasta.setText(sfechaHasta);
        horaHasta.setText(shoraHasta);
    }


    /**
     * Verifica los campos que los campos obligatorios del formulario estén cubiertos para
     * poder proceder a realizar el alta del evento
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

            LogCat.debug("Fecha desde:  " + fd);
            LogCat.debug("Fecha hasta:  " + fh);


            LogCat.debug("Fecha desde timeinmillis:  " + desde.getTimeInMillis());
            LogCat.debug("Fecha hasta timeinmillis:  " + hasta.getTimeInMillis());
            if(desde.getTimeInMillis()>hasta.getTimeInMillis()) {
                AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.err_fecha_hasta_anterior_desde)).show();

            } else {

                try {
                    // Se procede a dar de alta el evento en base de datos
                    EventoVO evento = new EventoVO();
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

                    SaveEventAsyncTask task = new SaveEventAsyncTask();
                    task.execute(params);

                    ResponseAsyncTask res = task.get();
                    if(res.getStatus()==0) {
                        // Se devuelve un Intent al activity ActividadPrincipal
                        Intent data = new Intent();
                        setResult(RESULT_OK, data);
                        finish();
                    } else {

                        AlertDialogHelper.crearDialogoAlertaAdvertencia(this,getString(R.string.atencion),getString(R.string.err_grabar_evento_bbdd)).show();
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
