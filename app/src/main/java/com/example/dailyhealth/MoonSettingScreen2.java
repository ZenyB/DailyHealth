package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MoonSettingScreen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon_setting_screen2);

        NumberPicker MoonCyclePicker = findViewById(R.id.MoonCyclePickerButton);
        if (MoonCyclePicker != null) {
            MoonCyclePicker.setMinValue(25);
            MoonCyclePicker.setMaxValue(40);
            MoonCyclePicker.setWrapSelectorWheel(true);
            MoonCyclePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    String text = "Changed from " + oldVal + " to " + newVal;
                    Toast.makeText(MoonSettingScreen2.this, text, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}