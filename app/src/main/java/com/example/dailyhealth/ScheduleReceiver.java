package com.example.dailyhealth;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ScheduleReceiver extends BroadcastReceiver {

    //private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "SleepNotificationChannel";
    @Override
    public void onReceive(Context context, Intent intent) {
        // Xử lý hiển thị thông báo tại đây
        // Ví dụ: Sử dụng NotificationManagerCompat để hiển thị thông báo
        // hoặc sử dụng các cách hiển thị thông báo khác
        String title = intent.getStringExtra("title");
        String detail = intent.getStringExtra("detail");
        String id = intent.getStringExtra("id");
        intent.getBooleanExtra("is_update", false);
        Intent intent1 = new Intent(context, NavigationActivity.class);
        intent1.putExtra("navigate_to_schedule", true);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "444")
                .setSmallIcon(R.drawable.ic_calendar_foreground)
                .setContentTitle(title)
                .setContentText(detail)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        int notificationId = 4444;
        notificationManager.notify(Integer.parseInt(id), builder.build());
    }

}
