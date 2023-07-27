package com.example.dailyhealth;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dailyhealth.database.MoonHelper;

public class TestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Xử lý hiển thị thông báo tại đây
        // Ví dụ: Sử dụng NotificationManagerCompat để hiển thị thông báo
        // hoặc sử dụng các cách hiển thị thông báo khác
        int nhactruoc = 0;
        String query = "SELECT THOIGIANNHACTRUOC FROM MOON";
        MoonHelper moonHelper = new MoonHelper(context);
        Cursor cursor = moonHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                nhactruoc = cursor.getInt(0);
            }
        }
        String ngay = "hôm nay.";
        if (nhactruoc != 0) {
            ngay = "Kỳ kinh nguyệt sẽ đến trong " + nhactruoc + " ngày nữa.";
        }
        Intent intent1 = new Intent(context, NavigationActivity.class);
        intent1.putExtra("navigate_to_moon", true);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "444")
                .setSmallIcon(R.drawable.icon_menstruation)
                .setContentTitle("Dự đoán kinh nguyệt")
                .setContentText(ngay)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        int notificationId = 4444;
        notificationManager.notify(notificationId, builder.build());
    }

    private void cancelNotification(NotificationManagerCompat notificationManager, int notificationId, NotificationCompat.Builder builder) {
        notificationManager.notify(notificationId, builder.build());
    }
}
