package com.oscar.libutilities.utils.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.oscar.libutilities.utils.exception.NotificationException;
import com.oscar.libutilities.utils.log.LogCat;

import utilities.android.oscar.com.libutilities.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Clase con operaciones de utilidad a la hora de mostrar notificaciones en el
 * dispositivo
 *
 * Created by oscar on 11/02/17.
 */
public class NotificationUtils {

    private static int ID_NOTIFICACION = 1;


    /**
     * sendNotificacion
     * @param info NotificacionInfo
     * @param overwriteNotification True si hay que sobreescribir la notificción y false en caso contrario
     * @throws NotificationException
     */
    public static void sendNotification(NotificacionInfo info,boolean overwriteNotification) throws NotificationException {

        Context context      = info.getContext();
        String title         = info.getTitle();
        String desc          = info.getDescripcion();
        Integer icon         = info.getIcon();
        Intent intentDestino = info.getIntentDestino();

        try {
            LogCat.debug("sendNotification ====>");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            NotificationManager notificationManager =(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intentDestino, 0);

            builder.setTicker(context.getString(R.string.nuevoEvento) + " " + title);
            builder.setContentTitle(title);
            builder.setContentText(desc);
            builder.setVibrate(new long[]{100, 250, 100, 500});
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setSmallIcon(icon);
            notificationManager.notify(ID_NOTIFICACION, builder.build());
            if(!overwriteNotification) {
                ID_NOTIFICACION++; // Se genera un nuevo id de notificación para que no se sobreescriban
            }

        }catch(Exception e) {
            LogCat.error("Se ha producido un error al enviar una notificacion: " + e.getMessage());
            throw new NotificationException("Se ha producido un error al enviar una notificacion: " + e.getMessage(),e);
        }
    }

}
