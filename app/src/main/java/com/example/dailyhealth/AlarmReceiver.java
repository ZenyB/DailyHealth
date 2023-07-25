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
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    // Hằng số cho hành động dừng tiếng chuông
    public static final String STOP_RINGTONE = "com.example.dailyhealth.STOP_RINGTONE";
    public static final String START_RINGTONE = "com.example.dailyhealth.START_RINGTONE";
    public static final String RINGTONE_URI_EXTRA = "ringtone_uri";

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
        if (intent != null) {
            String action = intent.getAction();
            if (action != null && action.equals(START_RINGTONE)) {
                // Nếu nhận thông báo báo thức từ hệ thống và có dữ liệu Uri tiếng chuông
                Uri alarm = intent.getParcelableExtra(RINGTONE_URI_EXTRA);
                if (alarm != null) {
                    // Khởi tạo và phát tiếng chuông với Uri đã nhận được
                    startRingtone(context, alarm);
                } else {
                    // Nếu không nhận được Uri tiếng chuông, sử dụng tiếng chuông mặc định
                    startDefaultRingtone(context);
                }
            } else if (action != null && action.equals(STOP_RINGTONE)) {
                // Nếu nhận thông báo yêu cầu dừng tiếng chuông từ SleepManagement
                stopRingtone(context);

            }
        }
    }

    private void startRingtone(Context context, Uri alarmUri) {
        // Dừng tiếng chuông nếu đang phát
        stopRingtone(context);

        // Khởi tạo tiếng chuông
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        if (ringtone != null) {
            ringtone.play();
        }
    }

    private void startDefaultRingtone(Context context) {
        // Dừng tiếng chuông nếu đang phát
        stopRingtone(context);

        // Khởi tạo và phát tiếng chuông mặc định
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri != null) {
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            if (ringtone != null) {
                ringtone.play();
            }
        }
    }

    private void stopRingtone(Context context) {
        // Dừng tiếng chuông
        if (ringtone != null) {
            ringtone.stop();
        }
        else{
//            Intent intent = new Intent(this, SleepManagement.class);
//            Intent stopRingtoneIntent = new Intent(AlarmReceiver.STOP_RINGTONE);
//            this.sendBroadcast(stopRingtoneIntent);
            Log.i("Lỗi","LỖI");
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
    private void stopRingtone(Context context, Uri alarmUri) {
        // Dừng tiếng chuông
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
    }
}


