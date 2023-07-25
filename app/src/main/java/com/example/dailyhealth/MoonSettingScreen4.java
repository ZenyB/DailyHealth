package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.dailyhealth.database.MoonHelper;

public class MoonSettingScreen4 extends AppCompatActivity {

    private MoonHelper moonHelper = new MoonHelper(this);
    static int notice = 0;
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
//                    if(values[newVal].equals("Vào đúng ngày"))
//                        notice = 0;
//                    else if(values[newVal].equals("Trước 1 ngày"))
//                        notice = 1;
//                    else if(values[newVal].equals("Trước 2 ngày"))
//                        notice = 2;
//                    else if(values[newVal].equals("Trước 3 ngày"))
//                        notice = 3;
//                    else if(values[newVal].equals("Trước 4 ngày"))
//                        notice = 4;
//                    else if(values[newVal].equals("Trước 5 ngày"))
//                        notice = 5;
//                    else if(values[newVal].equals("Trước 6 ngày"))
//                        notice = 6;
//                    else if(values[newVal].equals("Trước 7 ngày"))
//                        notice = 7;
                    notice = newVal;
                }
            });
        }

    }

    public void goBackBtn(View view) {
        finish();
    }
    public void confirmBtn(View view)
    {
        String query = "UPDATE MOON SET (THOIGIANNHACTRUOC) = " + notice + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        Intent i = new Intent(getBaseContext(), MoonCalendar.class);
        startActivity(i);
    }

    public void notRemember(View view)
    {
        Intent i = new Intent(getBaseContext(), MoonCalendar.class);
        startActivity(i);
    }
}