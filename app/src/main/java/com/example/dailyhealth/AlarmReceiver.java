package com.example.dailyhealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Kích hoạt dịch vụ AlarmService khi báo thức được kích hoạt
        if (intent != null && intent.getAction() != null && intent.getAction().equals("ALARM_ACTION")) {
            Intent serviceIntent = new Intent(context, AlarmService.class);
            context.startService(serviceIntent);
        }
    }
}
