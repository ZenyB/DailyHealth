package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MoonSettingScreen4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon_setting_screen4);
        NumberPicker MoonNoticePicker = findViewById(R.id.MoonNoticePickerButton);
        if (MoonNoticePicker != null) {
            final String[] values = {"Vào đúng ngày", "Trước 1 ngày", "Trước 2 ngày", "Trước 3 ngày", "Trước 4 ngày", "Trước 5 ngày", "Trước 6 ngày", "Trước 1 tuần"};
            MoonNoticePicker.setMinValue(0);
            MoonNoticePicker.setMaxValue(values.length - 1);
            MoonNoticePicker.setDisplayedValues(values);
            MoonNoticePicker.setWrapSelectorWheel(true);
            MoonNoticePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    String text = "Changed from " + values[oldVal] + " to " + values[newVal];
                    Toast.makeText(MoonSettingScreen4.this, text, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}