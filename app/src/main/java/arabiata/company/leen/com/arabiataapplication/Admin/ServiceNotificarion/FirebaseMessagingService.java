package arabiata.company.leen.com.arabiataapplication.Admin.ServiceNotificarion;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import arabiata.company.leen.com.arabiataapplication.R;
import arabiata.company.leen.com.arabiataapplication.SplashActivity;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("message"));
    }

    private void showNotification(String message) {

        createNotificationChannel();
        Intent i = new Intent(this, SplashActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "com.example.asherif.sahlapp")
                .setAutoCancel(true).setContentIntent(pendingIntent)
                .setChannelId("com.example.asherif.sahlapp")
                .setContentTitle(getString(R.string.noti))
                .setContentText(message)
                .setSound(Uri.parse("android.resource://"+getPackageName()+"/raw/noti_sound"))
                .setSmallIcon(R.drawable.arabiata_logo2)
                .setBadgeIconType(R.drawable.arabiata__logo) //your app icon
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            Log.i("TAG", "showNotificationchannelid: "+"a");

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("com.example.asherif.sahlapp", name, importance);
            channel.setDescription(description);
            channel.enableLights(true);

            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

