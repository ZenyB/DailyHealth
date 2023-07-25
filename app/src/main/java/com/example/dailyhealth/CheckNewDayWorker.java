package com.example.dailyhealth;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.Calendar;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class CheckNewDayWorker extends Worker {
    private Context context;

    public CheckNewDayWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }
//    public CheckNewDayWorker(
//            @NonNull Context context,
//            @NonNull WorkerParameters params) {
//        super(context, params);
//    }

    @NonNull
    @Override
    public Result doWork() {
        // Load SharedPreferences
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(SplashScreen.PREFS_NAME, Context.MODE_PRIVATE);

        // Get the last run date from SharedPreferences
        long lastRunDate = prefs.getLong(SplashScreen.KEY_LAST_NEW_DAY_TIME, 0);

        // Get the current date
        long todayInMillis = System.currentTimeMillis();
        Calendar lastRunCalendar = Calendar.getInstance();
        lastRunCalendar.setTimeInMillis(lastRunDate);

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTimeInMillis(todayInMillis);

        if (todayCalendar.get(Calendar.YEAR) > lastRunCalendar.get(Calendar.YEAR)
                || (todayCalendar.get(Calendar.YEAR) == lastRunCalendar.get(Calendar.YEAR)
                && todayCalendar.get(Calendar.DAY_OF_YEAR) > lastRunCalendar.get(Calendar.DAY_OF_YEAR))) {
            // Perform actions that you want to execute when a new day starts.

            // Update weekInfo table with previous day data
            SplashScreen splashScreen = new SplashScreen();
            splashScreen.savePreviousDayData();

            // Reset user data in users table
            splashScreen.resetUserData();

            // Show notification for new day
            showNotificationAndDoWork();


            // Save the current date as the last run date in SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(SplashScreen.KEY_LAST_NEW_DAY_TIME, todayInMillis);
            editor.apply();
        }

        return Result.success();
    }

    // Method to show the notification
    private void showNotificationAndDoWork() {
        String CHANNEL_ID = "new_day_channel"; // Change this ID if you need to create a new channel for the notification
        CharSequence name = "New Day Channel";
        String description = "Channel for New Day Notification";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system. You can't change the importance or other notification behaviors after this.
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_sleep)
                .setContentTitle("Ngày mới")
                .setContentText("Ngày mới vui vẻ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder.build());
    }

}

