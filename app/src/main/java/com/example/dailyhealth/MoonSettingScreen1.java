package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.dailyhealth.database.MoonHelper;

import java.util.Calendar;

public class MoonSettingScreen1 extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private ImageButton btnBack;
    static int year;
    static int month;
    static int day;
    public static int pickYear;
    public static int pickMonth;
    public static int pickDay;
    private MoonHelper moonHelper = new MoonHelper(this);
    protected static String idSave = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon_setting_screen1);
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initDatePicker();


    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        month = month + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        pickDay = day;
        pickMonth = month;
        pickYear = year;
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                pickDay = day;
                pickMonth = month;
                pickYear = year;
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

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void goBackBtn(View view) {
        Intent intent = new Intent(getBaseContext(), NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    public void confirmBtn(View view)
    {
//            String query = "SELECT NGAYBATDAU, THANGBATDAU, NAMBATDAU FROM moon";
//            Cursor cursor = moonHelper.GetData(query);

        Intent i = new Intent(getBaseContext(), MoonSettingScreen2.class);
        startActivity(i);
    }

    public void notRemember(View view)
    {
        Intent i = new Intent(getBaseContext(), MoonSettingScreen2.class);
        startActivity(i);
    }
}