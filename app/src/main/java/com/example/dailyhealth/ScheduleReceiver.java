package com.example.dailyhealth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dailyhealth.database.ScheduleHelper;

public class ScheduleReceiver extends BroadcastReceiver {

    //private static final int NOTIFICATION_ID = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "1111";
    @Override
    public void onReceive(Context context, Intent intent) {
        // Xử lý hiển thị thông báo tại đây
        // Ví dụ: Sử dụng NotificationManagerCompat để hiển thị thông báo
        // hoặc sử dụng các cách hiển thị thông báo khác
//        Log.i("receive", "true");
        String title = "";
        String detail = "";
        String id = intent.getStringExtra("id");
        if (intent.hasExtra("id")) {
            id = intent.getStringExtra("id");
            Log.i("receiveID", id);
            // Xử lý giá trị id ở đây
        } else {
            Log.i("receiveID", "No ID found in intent.");
        }
        String query = "SELECT TIEUDE, GHICHU FROM SCHEDULE WHERE ID = " + id;
        ScheduleHelper scheduleHelper = new ScheduleHelper(context);
        Cursor cursor = scheduleHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                title = cursor.getString(0);
                detail = cursor.getString(1);
            }
        }
//        boolean isDelete = intent.getBooleanExtra("isDelete", false);
//        if (isDelete || intent.getAction().equals("is_delete")) {
//            Log.i("IS DELETE SUCCESS", "SUCCESS");
//            Toast.makeText(context, "Is Delete", Toast.LENGTH_SHORT).show();
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            //cancelNotification(notificationManager, Integer.getInteger(id));
//        } else
        Log.i("receiver", "true");
        Intent intent1 = new Intent(context, NavigationActivity.class);
        intent1.putExtra("navigate_to_schedule", true);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1111")
                .setSmallIcon(R.drawable.ic_calendar_foreground)
                .setContentTitle(title)
                .setContentText(detail)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        int notificationId = Integer.parseInt("123");
            notificationManager.notify(notificationId, builder.build());
    }
    public static void cancelNotification(NotificationManager notificationManager, int notificationId) {
        notificationManager.cancel(notificationId);
    }
}
