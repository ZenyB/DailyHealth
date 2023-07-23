package com.example.dailyhealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NewDayBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Một ngày mới đã bắt đầu!", Toast.LENGTH_SHORT).show();
        // Ghi mã lưu trữ dữ liệu tại đây (ví dụ: lưu ngày mới vào SharedPreferences)
    }
}