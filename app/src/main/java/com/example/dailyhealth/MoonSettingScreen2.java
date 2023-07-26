package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.dailyhealth.database.MoonHelper;

public class MoonSettingScreen2 extends AppCompatActivity {

    private MoonHelper moonHelper = new MoonHelper(this);
    static int cycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon_setting_screen2);
        cycle = 25;
        NumberPicker MoonCyclePicker = findViewById(R.id.MoonCyclePickerButton);
        if (MoonCyclePicker != null) {
            MoonCyclePicker.setMinValue(25);
            MoonCyclePicker.setMaxValue(40);
            MoonCyclePicker.setWrapSelectorWheel(true);
            MoonCyclePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    cycle = newVal;
                }
            });
        }

    }

    public void goBackBtn(View view) {
        finish();
    }
    public void confirmBtn(View view)
    {
//        String query = "SELECT NGAYBATDAU, THANGBATDAU, NAMBATDAU FROM moon";
//        Cursor cursor = moonHelper.GetData(query);
        Intent i = new Intent(getBaseContext(), MoonSettingScreen3.class);
        startActivity(i);
    }

    public void notRemember(View view)
    {
        Intent i = new Intent(getBaseContext(), MoonSettingScreen3.class);
        startActivity(i);
    }
}