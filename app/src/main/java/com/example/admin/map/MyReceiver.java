package com.example.admin.map;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Ohanuke")
                .setContentText("Ohanuke - wiki web page")
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentIntent(getPendingIntent(context));

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(123, builder.build());
    }

    private static PendingIntent getPendingIntent(Context context) {

        String  url = "https://en.wikipedia.org/wiki/Ohanuke";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));

        return PendingIntent.getActivity(
                context,
                1123,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
