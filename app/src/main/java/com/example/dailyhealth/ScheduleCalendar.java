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

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dailyhealth.database.ScheduleHelper;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScheduleCalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener, ScheduleEventAdapter.OnItemClick{

    protected static ArrayList<ScheduleEvent> scheduleEvents = new ArrayList<>();
    protected static ArrayList<ScheduleEvent> schedule = new ArrayList<>();
    private ScheduleHelper scheduleHelper = new ScheduleHelper(this);
    private static ScheduleHelper scheduleHelper1;
    private TextView monthYearText;
    private TextView YearText;
    private RecyclerView calendarRecyclerView;
    private RecyclerView r;
    private ImageButton btnBack;
    //    private ListView eventListView;
    private String[] item = {"Sửa", "Xóa"};
    private LocalDate today = LocalDate.now();
    protected static String id = "";
    private void removedata(){
        if (!id.isEmpty()) {
            String query = "DELETE FROM schedule WHERE ID = '" + id + "'";
            scheduleHelper.QueryData(query);
        }
    }

    public void updateSchedule(){
        scheduleEvents.clear();

        String query = "SELECT * FROM schedule";
        //cheduleHelper1 = new ScheduleHelper();
        Cursor cursor = scheduleHelper1.GetData(query);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Log.i("aaaaaa", cursor.getString(0));
                if (CalendarUtils.selectedDate.getDayOfMonth() == cursor.getInt(4) && cursor.getInt(5) == CalendarUtils.selectedDate.getMonthValue() && cursor.getInt(6) == CalendarUtils.selectedDate.getYear()){
                    scheduleEvents.add(new ScheduleEvent(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8)));
                }
            }
        }
        r.setAdapter(new ScheduleEventAdapter(this, scheduleEvents, this));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        scheduleHelper1 = new ScheduleHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_calendar);
        CalendarUtils.selectedDate = LocalDate.now();

//        scheduleEvents.add(new ScheduleEvent("Đi ăn cưới", "11:35","Note lại đi 500k", "Xóm 100"));
//        scheduleEvents.add(new ScheduleEvent("Đi chơi với gái", "13:35","Note lại đi 1tr", "Phòng 2168"));

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        r = (RecyclerView) findViewById(R.id.scheduleCalendarRV);
        r.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        r.setAdapter(new ScheduleEventAdapter(this, scheduleEvents, this));
        ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.floatingActionButton), "translationY", -40f);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
        animator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        initWidgets();
        setWeekView();
        updateSchedule();
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
        scheduleEvents.clear();

        String query = "SELECT * FROM schedule ORDER BY TIENG, TONGPHUT";
        Cursor cursor = scheduleHelper.GetData(query);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Log.i("aaaaaa", cursor.getString(0));
                if (CalendarUtils.selectedDate.getDayOfMonth() == cursor.getInt(4) && cursor.getInt(5) == CalendarUtils.selectedDate.getMonthValue() && cursor.getInt(6) == CalendarUtils.selectedDate.getYear()){
                    scheduleEvents.add(new ScheduleEvent(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8)));
                }
            }
        }

//        for (ScheduleEvent scheduleEvent: schedule){
//            if (scheduleEvent.getDay() == CalendarUtils.selectedDate.getDayOfMonth()){
//                scheduleEvents.add(scheduleEvent);
//            }
//        }
//        //Get su kien tu database va cap nhat lai UI o day
//        scheduleEvents.add(new ScheduleEvent("Đi ăn cưới", "11:35","Note lại đi 500k", "Xóm 100"));
//        scheduleEvents.add(new ScheduleEvent("Đi chơi với gái", "13:35","Note lại đi 1tr", "Phòng 2168"));
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
        updateSchedule();
    }


    @Override
    public void onScheduleClick(int position) {
        scheduleEvents.get(position);

        ScheduleEvent schedule = scheduleEvents.get(position);
        ScheduleEventSetting.idSave = schedule.getId().toString();
        Log.i("AaAaaaa", ScheduleEventSetting.idSave);
        Intent i = new Intent(getBaseContext(), ScheduleEventSetting.class);
        startActivity(i);
    }

    public void addEventBtn(View view) {
        Intent i = new Intent(getBaseContext(), ScheduleEventSetting.class);
        startActivity(i);
    }

//    private void backButton(View view) { finish();}

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