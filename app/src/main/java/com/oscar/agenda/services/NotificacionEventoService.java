package com.oscar.agenda.services;

import android.app.IntentService;
import android.content.Intent;

import com.oscar.agenda.activities.EdicionDetalleActivity;
import com.oscar.agenda.database.entity.EventoVO;
import com.oscar.agenda.database.helper.EventHelper;
import com.oscar.agenda.utils.Constantes;
import com.oscar.libutilities.utils.log.LogCat;
import com.oscar.libutilities.utils.notification.NotificacionInfo;
import com.oscar.libutilities.utils.notification.NotificationUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase NotificacionEventoService que notifica a un usuario de cuando tiene un evento pendiente
 * Created by oscar on 07/02/17.
 */
public class NotificacionEventoService extends IntentService {

    private Timer timer = null;

    /**
     * Constructor
     */
    public NotificacionEventoService() {
        super("NotificacionEventoService");
        timer = new Timer();
    }


    /**
     * onHandleIntent que recupera los eventos cada 5 minutos y notifica al usuario de los mismos
     * si es necesario
     * @param intent Intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            sendNotificationPerEvent();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Método que se encarga de recuperar los eventos de la base de datos para poder
     * notificar al usuario de la proximidad de la existencia de dicho evento
     */
    private void sendNotificationPerEvent() {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            try {
                LogCat.debug("NotificacionEventoService ejecución de timer ===>");

                // Para la fecha actual se recuperan los eventos que puedan existir, para poder notificar al usuario
                Calendar fecha = Calendar.getInstance();

                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                LogCat.debug("NotificacionEventoService.Fecha para la que se notifican eventos: " + sf.format(fecha.getTime()));

                EventHelper helper = new EventHelper(getApplicationContext());
                List<EventoVO> eventos =  helper.getEventosFechaHoraDesde(fecha);

                for(int i=0;eventos!=null && i<eventos.size();i++) {
                    EventoVO evento = eventos.get(i);
                    String nombreEvento = evento.getNombre();

                    /** Envio de la notificación para el evento actual **/
                    String descripcion = evento.getFechaDesde() + " " + evento.getHoraDesde();
                    Intent intentDestino = new Intent(getApplicationContext(), EdicionDetalleActivity.class);

                    NotificacionInfo info = new NotificacionInfo(nombreEvento,descripcion,getApplicationContext(),intentDestino);
                    info.setIcon(android.R.mipmap.sym_def_app_icon);

                    NotificationUtils.sendNotification(info,false);

                }

                LogCat.debug("NotificacionEventoService.Número de eventos recuperados: " + eventos.size());
                LogCat.debug("NotificacionEventoService ejecución de timer <===");


            } catch(Exception e) {
                e.printStackTrace();
            }

            }
        },0, Constantes.INTERVAL_TIMER_EVENT_NOTIFICATION);
    }


}