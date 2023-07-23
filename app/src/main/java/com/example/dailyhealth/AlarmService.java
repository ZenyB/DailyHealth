package com.example.dailyhealth;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "alarm_channel";

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals("ALARM_ACTION")) {
            // Xử lý hành động khi báo thức được kích hoạt
            showNotification("Báo thức đã được kích hoạt!");
        }
    }

    private void showNotification(String message) {
        // Tạo notification channel (chỉ cần làm một lần)
        createNotificationChannel();

        // Tạo notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Báo thức")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_alarm_setting)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        // Hiển thị notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm Channel";
            String description = "Channel for Alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
