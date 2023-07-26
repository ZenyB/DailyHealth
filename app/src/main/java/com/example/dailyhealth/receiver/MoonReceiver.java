package com.example.dailyhealth.receiver;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dailyhealth.MoonCalendar;
import com.example.dailyhealth.NavigationActivity;
import com.example.dailyhealth.R;
import com.example.dailyhealth.database.MoonHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoonReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 444;
    private static final String CHANNEL_ID = "Thông báo kinh nguyệt";

    static int nhactruoc = 0;
    Context contextt;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Lấy ngày được chọn
        contextt = context;
        String selectedDateStr = intent.getStringExtra("SELECTED_DATE");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
//            String query = "SELECT THOIGIANNHACTRUOC FROM MOON";
//            MoonHelper moonHelper = new MoonHelper(contextt);
//            Cursor cursor = moonHelper.GetData(query);
//            if (cursor.getCount() > 0) {
//                while (cursor.moveToNext()) {
//                    nhactruoc = cursor.getInt(0);
//                }
//            }
            nhactruoc = 2;
            // Chuyển đổi ngày được chọn sang đối tượng Date
            Date selectedDate = sdf.parse(selectedDateStr);

            // Lấy ngày hiện tại
            Calendar calendarToday = Calendar.getInstance();
            Date currentDate = calendarToday.getTime();

            // Tính số ngày giữa ngày hiện tại và ngày được chọn
            long timeDiff = selectedDate.getTime() - currentDate.getTime();
            long daysDiff = timeDiff / (1000 * 60 * 60 * 24);

            // Kiểm tra xem nếu là 2 ngày nữa thì gửi thông báo
            if (daysDiff == nhactruoc) {
                showNotification(context, selectedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotification(Context context, Date selectedDate) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // Tạo NotificationChannel cho Android 8.0 trở lên
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Moon Channel";
//            String description = "Channel for Moon notifications";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//
//            if (notificationManager != null) {
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
        createNotificationChannel();

        // Tạo PendingIntent cho thông báo khi được nhấn
        Intent notificationIntent = new Intent(context, NavigationActivity.class);
        notificationIntent.putExtra("navigate_to_moon", true);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        String ngay = "hôm nay.";
        if (nhactruoc != 0) {
            ngay = "Kỳ kinh nguyệt sẽ đến trong " + nhactruoc + " ngày nữa.";
        }

        Notification builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_menstruation)
                .setContentTitle("Dự đoán kinh nguyệt")
                .setContentText("Kỳ kinh nguyệt sẽ đến trong " + ngay)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID, builder);
//                        .setSmallIcon(R.drawable.icon_sleep)
//                        .setContentTitle("Sleep")
//                        .setContentText("You have to sleep more")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
//                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                        .setAutoCancel(true);
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        // Tạo thông báo
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            String query = "SELECT THOIGIANNHACTRUOC FROM MOON";
//            MoonHelper moonHelper = new MoonHelper(contextt);
//            Cursor cursor = moonHelper.GetData(query);
//            if (cursor.getCount() > 0) {
//                while (cursor.moveToNext()) {
//                    nhactruoc = cursor.getInt(0);
//                }
//            }

//            String ngay = "hôm nay.";
//            if (nhactruoc != 0) {
//                ngay = nhactruoc + " ngày nữa.";
//            }
//            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
//                        .setSmallIcon(R.drawable.icon_menstruation)
//                        .setContentTitle("Dự đoán kinh nguyệt")
//                        .setContentText("Kỳ kinh nguyệt sẽ đến trong " + ngay)
//                        .setContentIntent(pendingIntent)
//                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                        .setAutoCancel(true)
//                        .build();
//
//
////            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
////                    .setContentTitle("Dự đoán kinh nguyệt")
////                    .setContentText("Kỳ kinh nguyệt sẽ đến trong " + ngay)
////                    .setSmallIcon(R.drawable.icon_menstruation)
////                    .setContentIntent(pendingIntent)
////                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
////                    .setAutoCancel(true)
////                    .build();
//        }
//
//        // Hiển thị thông báo
//        if (notificationManager != null) {
//            notificationManager.notify(NOTIFICATION_ID, notification);
//        }
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    // Lên lịch thông báo
    public static void scheduleNotification(Context context, String selectedDate, int ngaynhactruoc) {
        Log.i("NOTIFICATION", selectedDate);
        nhactruoc = ngaynhactruoc;
        Intent intent = new Intent(context, MoonReceiver.class);
        intent.putExtra("SELECTED_DATE", selectedDate);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // Lên lịch thông báo 2 ngày trước ngày được chọn
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date date = sdf.parse(selectedDate);
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, -ngaynhactruoc);
                long triggerTime = calendar.getTimeInMillis();
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //String CHANNEL_ID = "MOON";
            CharSequence name = "Thông báo kinh nguyệt";
            String description = "Thông báo kinh nguyệt";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = contextt.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Hủy thông báo
    public static void cancelNotification(Context context) {
        Intent intent = new Intent(context, MoonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
