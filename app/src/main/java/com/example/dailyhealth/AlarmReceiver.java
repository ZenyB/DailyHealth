package com.example.dailyhealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 123;
    private static final String ALARM_CHANNEL_ID = "ALARM_CHANNEL";
    private Handler handler;
    private Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Tạo thông báo khi báo thức được kích hoạt
        createNotificationChannel(context);

        Notification.Builder notificationBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(context, ALARM_CHANNEL_ID)
                    .setSmallIcon(R.drawable.alarm)
                    .setContentTitle("Báo thức đã đến!")
                    .setContentText("Nhấn để tắt báo thức.")
                    .setAutoCancel(true);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }

        // Chờ 3 giây trước khi dừng tiếng chuông
        handler = new Handler();
        handler.postDelayed(this::stopRingtone, 3000);

        // Khởi tạo tiếng chuông
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri != null) {
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            if (ringtone != null) {
                ringtone.play();
            }
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm Channel";
            String description = "Channel for Alarm notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(ALARM_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
    private void stopRingtone() {
        // Dừng tiếng chuông
        // Đoạn code dừng tiếng chuông, có thể thực hiện bằng cách gọi phương thức trong lớp SleepManagement hoặc AlarmReceiver
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
    }
}


