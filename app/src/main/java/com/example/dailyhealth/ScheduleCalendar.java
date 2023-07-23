package com.example.dailyhealth;

import static com.example.dailyhealth.CalendarUtils.daysInWeekArray;
import static com.example.dailyhealth.CalendarUtils.highlightDate;
import static com.example.dailyhealth.CalendarUtils.monthYearFromDate;
import static com.example.dailyhealth.CalendarUtils.yearFromDate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScheduleCalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener, ScheduleEventAdapter.OnItemClick{

    private ArrayList<ScheduleEvent> scheduleEvents = new ArrayList<>();
    private TextView monthYearText;
    private TextView YearText;
    private RecyclerView calendarRecyclerView;
    private RecyclerView r;
    //    private ListView eventListView;
    private LocalDate today = LocalDate.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_calendar);
        CalendarUtils.selectedDate = LocalDate.now();

        scheduleEvents.add(new ScheduleEvent("Đi ăn cưới", "11:35","Note lại đi 500k", "Xóm 100"));
        scheduleEvents.add(new ScheduleEvent("Đi chơi với gái", "13:35","Note lại đi 1tr", "Phòng 2168"));

        r = (RecyclerView) findViewById(R.id.scheduleCalendarRV);
        r.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        r.setAdapter(new ScheduleEventAdapter(this, scheduleEvents, this));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        initWidgets();
        setWeekView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.moonCalendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        YearText = findViewById(R.id.YearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        YearText.setText(yearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        //setEventAdpater();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
        //Get su kien tu database va cap nhat lai UI o day
        scheduleEvents.add(new ScheduleEvent("Đi ăn cưới", "11:35","Note lại đi 500k", "Xóm 100"));
        scheduleEvents.add(new ScheduleEvent("Đi chơi với gái", "13:35","Note lại đi 1tr", "Phòng 2168"));
        r.setAdapter(new ScheduleEventAdapter(this, scheduleEvents, this));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        setEventAdpater();
    }

    public void todayGoback(View view) {
        CalendarUtils.selectedDate = today;
        setWeekView();
    }


    @Override
    public void onScheduleClick(int position) {

    }

//    private void setEventAdpater()
//    {
//        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
//        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
//        eventListView.setAdapter(eventAdapter);
//    }
//
//    public void newEventAction(View view)
//    {
//        startActivity(new Intent(this, EventEditActivity.class));
//    }
}