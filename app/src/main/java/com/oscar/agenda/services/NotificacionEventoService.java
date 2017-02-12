package com.oscar.agenda.services;

import android.app.IntentService;
import android.content.Intent;

import com.oscar.agenda.activities.EdicionDetalleActivity;
import com.oscar.agenda.utils.Constantes;
import com.oscar.libutilities.utils.log.LogCat;
import com.oscar.libutilities.utils.notification.NotificacionInfo;
import com.oscar.libutilities.utils.notification.NotificationUtils;

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

            Intent intentDestino = new Intent(getApplicationContext(), EdicionDetalleActivity.class);
            NotificacionInfo info = new NotificacionInfo("Titulo","Tienes que ir a cargar",getApplicationContext(),intentDestino);
            info.setIcon(android.R.mipmap.sym_def_app_icon);
            
            NotificationUtils.sendNotification(info);
            //sendNotification();
            sendNotificationPerEvent();

            // Se invoca al helper de BD directamente sin hacerlo a través de un AsynTask, ya
            // que este última desde un IntentService no funciona
 /*
                    EventHelper helper = new EventHelper(getApplicationContext());
                    List<EventoVO> eventos = helper.getEventos(fecha);

                    LogCat.debug("eventos: " + eventos);
                    */

        } catch(Exception e) {
            e.printStackTrace();
        }


        LogCat.debug("NotificacionEventoService.onHandleIntent <====");
    }


    /**
     * Método que se encarga de recuperar los eventos de la base de datos para poder
     * notificar al usuario de la proximidad de la existencia de dicho evento
     */
    private void sendNotificationPerEvent() {

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                LogCat.debug("Ejecución de timer ===>");

            }
        },0, Constantes.INTERVAL_TIMER_EVENT_NOTIFICATION);
    }


}