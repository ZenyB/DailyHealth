package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MoonSettingScreen3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon_setting_screen3);
        NumberPicker MoonLengthPicker = findViewById(R.id.MoonLengthPickerButton);
        if (MoonLengthPicker != null) {
            MoonLengthPicker.setMinValue(2);
            MoonLengthPicker.setMaxValue(10);
            MoonLengthPicker.setWrapSelectorWheel(true);
            MoonLengthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    String text = "Changed from " + oldVal + " to " + newVal;
                    Toast.makeText(MoonSettingScreen3.this, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}