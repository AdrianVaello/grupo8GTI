package com.example.navigationdrawtest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificacionTrayectos extends Service {
    private NotificationManager notificationManager;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Notificacion creada", Toast.LENGTH_SHORT).show();

    }
    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CANAL_ID, "Mis Notificaciones", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del notificacion");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this, CANAL_ID)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Trayecto iniciado")
                .setContentText("El trayecto se ha iniciado con éxito. ¡Disfruta del viaje!")

                .setWhen(System.currentTimeMillis() + 1000 * 60 * 60)
                .setContentInfo("Click para más info");
        PendingIntent intencionPendiente = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        notificacion.setContentIntent(intencionPendiente);
        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
         //startForeground(NOTIFICACION_ID, notificacion.build());
        //Toast.makeText(this, "Servicio arrancado " + idArranque, Toast.LENGTH_SHORT).show();

        return START_STICKY;


    }

    @Override
    public IBinder onBind(Intent intencion) {
        return null;
    }
}
