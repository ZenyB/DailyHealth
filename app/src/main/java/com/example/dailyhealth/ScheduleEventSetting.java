package com.example.dailyhealth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;

public class ScheduleEventSetting extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton;
    int year;
    int month;
    int day;
    private ScheduleEvent scheduleEvent;
    private EditText titleEventText;
    private EditText timeEventText;
    private EditText locationEventText;
    private EditText detailEventText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_setting);
        titleEventText = findViewById(R.id.titleEventTV);
        locationEventText = findViewById(R.id.locationEventTV);
        detailEventText = findViewById(R.id.detailEventTV);
        dateButton = findViewById(R.id.dateEventPickerButton);
        dateButton.setText(getTodaysDate());
        ((Button)findViewById(R.id.timeEventTV)).setText(getNowTime());
        initDatePicker();
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        month = month + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getNowTime(){
        return (LocalTime.now().getHour() < 10? "0":"") + LocalTime.now().getHour() + ":" + (LocalTime.now().getMinute() < 10? "0":"") + LocalTime.now().getMinute();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        //Time Picker
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ((Button)findViewById(R.id.timeEventTV)).setText((hourOfDay < 10? "0":"") + hourOfDay + ":" + (minute < 10? "0":"") + minute);
            }
        };

        LocalTime time = LocalTime.now();
        timePickerDialog = new TimePickerDialog(this, timeSetListener, time.getHour(), time.getMinute(), true);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Tháng 1";
        if(month == 2)
            return "Tháng 2";
        if(month == 3)
            return "Tháng 3";
        if(month == 4)
            return "Tháng 4";
        if(month == 5)
            return "Tháng 5";
        if(month == 6)
            return "Tháng 6";
        if(month == 7)
            return "Tháng 7";
        if(month == 8)
            return "Tháng 8";
        if(month == 9)
            return "Tháng 9";
        if(month == 10)
            return "Tháng 10";
        if(month == 11)
            return "Tháng 11";
        if(month == 12)
            return "Tháng 12";

        return "Tháng 1";
    }


    public void cancelBtn(View view) {
        finish();
    }

    public void openEventDatePicker(View view) {
        datePickerDialog.show();
    }

    public void openTimePicker(View view) {
        timePickerDialog.show();
    }
}