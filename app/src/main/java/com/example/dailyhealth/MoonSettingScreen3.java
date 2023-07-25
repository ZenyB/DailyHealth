package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.dailyhealth.database.MoonHelper;

public class MoonSettingScreen3 extends AppCompatActivity {

    private MoonHelper moonHelper = new MoonHelper(this);
    static int moonCycle = 2;
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
//                    String text = "Changed from " + oldVal + " to " + newVal;
//                    Toast.makeText(MoonSettingScreen3.this, text, Toast.LENGTH_SHORT).show();
                    moonCycle = newVal;
                }
            });
        }
    }

    public void goBackBtn(View view) {
        finish();
    }
    public void confirmBtn(View view)
    {
        String query = "UPDATE MOON SET (TRUNGBINHKINHNGUYET) = " + moonCycle + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        Intent i = new Intent(getBaseContext(), MoonSettingScreen4.class);
        startActivity(i);
    }

    public void notRemember(View view)
    {
        Intent i = new Intent(getBaseContext(), MoonSettingScreen4.class);
        startActivity(i);
    }
}