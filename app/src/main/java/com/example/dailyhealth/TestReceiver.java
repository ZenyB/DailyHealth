package com.example.dailyhealth;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Xử lý hiển thị thông báo tại đây
        // Ví dụ: Sử dụng NotificationManagerCompat để hiển thị thông báo
        // hoặc sử dụng các cách hiển thị thông báo khác
        String id = intent.getStringExtra("id");
        intent.getBooleanExtra("is_update", false);
        Intent intent1 = new Intent(context, NavigationActivity.class);
        intent1.putExtra("navigate_to_moon", true);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "444")
                .setSmallIcon(R.drawable.icon_sleep)
                .setContentTitle("Sleep")
                .setContentText("You have to sleep more")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        int notificationId = 4444;
        notificationManager.notify(Integer.parseInt(id), builder.build());
    }

//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is not in the Support Library.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //String CHANNEL_ID = "MOON";
//            CharSequence name = getString(R.string.moon_channel_name);
//            String description = getString(R.string.moon_channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system. You can't change the importance
//            // or other notification behaviors after this.
//            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
}
