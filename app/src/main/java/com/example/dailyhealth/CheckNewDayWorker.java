package com.example.dailyhealth;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CheckNewDayWorker extends Worker {

    public CheckNewDayWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

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
            //ResetNum()

            // Save the current date as the last run date in SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(SplashScreen.KEY_LAST_NEW_DAY_TIME, todayInMillis);
            editor.apply();
        }

        return Result.success();
    }
}
