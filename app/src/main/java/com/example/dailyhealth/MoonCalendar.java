package com.example.dailyhealth;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class MoonCalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener {
//
//    private TextView monthYearText;
//    private RecyclerView calendarRecyclerView;
//    private LocalDate selectedDate;
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_moon_calendar);
//        initWidgets();
//        selectedDate = LocalDate.now();
//        setMonthView();
//    }
//    private void initWidgets()
//    {
//        calendarRecyclerView = findViewById(R.id.moonCalendarRecyclerView);
//        monthYearText = findViewById(R.id.monthYearTV);
//    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void setMonthView()
//    {
//        monthYearText.setText(monthYearFromDate(selectedDate));
//        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
//        ArrayList<String> highlightedDays = new ArrayList<>();
////        highlightedDays.add("3");
////        highlightedDays.add("5");
//        CalendarAdapter calendarAdapter = new CalendarAdapter(highlightedDays, (CalendarAdapter.OnItemListener) this);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
//        calendarRecyclerView.setLayoutManager(layoutManager);
//        calendarRecyclerView.setAdapter(calendarAdapter);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private ArrayList<String> daysInMonthArray(LocalDate date)
//    {
//        ArrayList<String> daysInMonthArray = new ArrayList<>();
//        YearMonth yearMonth = YearMonth.from(date);
//
//        int daysInMonth = yearMonth.lengthOfMonth();
//
//        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
//        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
//
//        for(int i = 1; i <= 42; i++)
//        {
//            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
//            {
//                daysInMonthArray.add("");
//            }
//            else
//            {
//                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
//            }
//        }
//        return  daysInMonthArray;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private String monthYearFromDate(LocalDate date)
//    {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
//        return date.format(formatter);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void previousMonthAction(View view)
//    {
//        selectedDate = selectedDate.minusMonths(1);
//        setMonthView();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void nextMonthAction(View view)
//    {
//        selectedDate = selectedDate.plusMonths(1);
//        setMonthView();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onItemClick(int position, String dayText)
//    {
//        if(!dayText.equals(""))
//        {
//            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//        }
//    }
//}


import static com.example.dailyhealth.CalendarUtils.cycleDays;
import static com.example.dailyhealth.CalendarUtils.daysInMonthArray;
import static com.example.dailyhealth.CalendarUtils.highlightDate;
import static com.example.dailyhealth.CalendarUtils.monthYearFromDate;
import static com.example.dailyhealth.CalendarUtils.moonDays;
import static com.example.dailyhealth.CalendarUtils.mooning;
import static com.example.dailyhealth.CalendarUtils.selectedDate;
import static com.example.dailyhealth.CalendarUtils.startButtonDate;
import static com.example.dailyhealth.CalendarUtils.startDate;

import static java.time.temporal.ChronoUnit.DAYS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyhealth.database.MoonHelper;
import com.example.dailyhealth.database.UserHelper;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;


public class MoonCalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button startBtn;
    private Button endBtn;
    private static MoonHelper moonHelper;
    private static CalendarAdapter calendarAdapter;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon_calendar);
        startBtn = findViewById(R.id.moonStartButton);
        endBtn = findViewById(R.id.moonEndButton);
        CalendarUtils.selectedDate = LocalDate.now();
        moonHelper = new MoonHelper(this);
        String query = "SELECT HANHKINH FROM MOON WHERE ID = '1'";
        Cursor cursor = moonHelper.GetData(query);

        if(cursor.getCount() > 0)
        {
            while (cursor.moveToNext()){
                mooning = cursor.getInt(0);
            }
        }
        if(mooning == 0)
        {
            endBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            startBtn.setVisibility(View.GONE);
            endBtn.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        initWidgets();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.moonCalendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onStartBtn(View view)
    {
        LocalDate today = LocalDate.now();
        CalendarUtils.startButtonDate = today;
        moonHelper = new MoonHelper(this);
        String query = "UPDATE MOON SET (HANHKINH) = " + 1 + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (NGAY) = " + startButtonDate.getDayOfMonth() + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (THANG) = " + startButtonDate.getMonthValue() + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (NAM) = " + startButtonDate.getYear() + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        CalendarUtils.mooning = 1;
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(startButtonDate);
        calendarAdapter.notifyItemChanged(daysInMonth.indexOf(startButtonDate));
        startBtn.setVisibility(View.GONE);
        endBtn.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onEndBtn(View view)
    {
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(startDate);
        CalendarUtils.daysMooningArray().clear();
        CalendarUtils.daysInMoonArray().clear();
        calendarAdapter.notifyItemRangeChanged(daysInMonth.indexOf(startDate.plusDays(cycleDays)), moonDays);
        int temp = (int) DAYS.between(startDate, CalendarUtils.startButtonDate);
        cycleDays = (cycleDays + temp + 1) / 2;
        int tempo = (int)DAYS.between(LocalDate.now(), startButtonDate) + 5;
        Log.i("tempo", Integer.toString(tempo));
        moonDays = (moonDays + tempo + 1) / 2;
        startDate = startButtonDate;
        String query = "UPDATE MOON SET (TRUNGBINHKINHNGUYET) = " + moonDays + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (HANHKINH) = " + 0 + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (NGAYBATDAU) = " + startDate.getDayOfMonth() + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (THANGBATDAU) = " + startDate.getMonthValue() + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (NAMBATDAU) = " + startDate.getYear() + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        query = "UPDATE MOON SET (TRUNGBINHCHUKY) = " + cycleDays + " WHERE ID = '1'";
        moonHelper.QueryData(query);
        CalendarUtils.mooning = 0;
        calendarAdapter.notifyItemChanged(daysInMonth.indexOf(startDate));
        endBtn.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);
    }

    public void goBackBtn(View view)
    {
        finish();
    }

    public void restartSetting(View view)
    {
        Intent i = new Intent(getBaseContext(), MoonSettingScreen1.class);
        startActivity(i);
    }
}