package com.example.dailyhealth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dailyhealth.database.ScheduleHelper;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class ScheduleEventSetting extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    protected static String idSave = "";
    private TimePickerDialog timePickerDialog;
    private Button dateButton, saveButton;
    private ScheduleHelper scheduleHelper = new ScheduleHelper(this);
    static int year;
    static int month;
    static int day;
    static int hours;
    static int totalMinutes;
    private ScheduleEvent scheduleEvent;
    private EditText titleEventText;
    private EditText timeEventText;
    private EditText locationEventText;
    private EditText detailEventText;
    protected static String id;

    private ScheduleEventAdapter scheduleEventAdapter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event_setting);
        titleEventText = findViewById(R.id.titleEventTV);
        locationEventText = findViewById(R.id.locationEventTV);
        detailEventText = findViewById(R.id.detailEventTV);
        saveButton = findViewById(R.id.saveButton);
        dateButton = findViewById(R.id.dateEventPickerButton);
        dateButton.setText(getTodaysDate());
        ((Button)findViewById(R.id.timeEventTV)).setText(getNowTime());
        initDatePicker();
        setSchedule();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEventText.getText().toString().isEmpty()){
                    String errorMessage = "Vui lòng nhập tiêu đề!";
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ScheduleEventSetting.this);
                    builder.setTitle("Thông báo!!!")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                if (idSave.isEmpty()) {
                    String query = "SELECT * FROM schedule";
                    Cursor cursor = scheduleHelper.GetData(query);
                    id = "00001";

                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            if (Integer.parseInt(id) < Integer.parseInt(cursor.getString(0)))
                                id = String.format("%05d", Integer.parseInt(cursor.getString(0)));
                        }
                        id = String.format("%05d", Integer.parseInt(id) + 1);
                    }

                    //ScheduleCalendar.schedule.add(new ScheduleEvent(id, titleEventText.getText().toString(),detailEventText.getText().toString(), locationEventText.getText().toString(), day, month, year , hours, totalMinutes));

                    query = "INSERT INTO schedule (ID, TIEUDE, GHICHU, DIADIEM, NGAY, THANG, NAM, TIENG, TONGPHUT) " +
                            "VALUES ('" + id + "', '" + titleEventText.getText().toString() + "', '" + detailEventText.getText().toString() + "', '" + locationEventText.getText().toString() + "', " + day + ", " + month + ", " + year + ", " + hours + ", " + totalMinutes + ")";
                    scheduleHelper.QueryData(query);
                    scheduleNotification(day , month, year, id);
                    finish();
                }
                else {
                    String query = "UPDATE schedule SET TIEUDE = '" + titleEventText.getText().toString() + "', " +
                            "GHICHU = '" + detailEventText.getText().toString() + "', " +
                            "DIADIEM = '" + locationEventText.getText().toString() + "', " +
                            "NGAY = " + day + ", THANG = " + month + ", NAM = " + year + ", " +
                            "TIENG = " + hours + ", TONGPHUT = " + totalMinutes + " WHERE ID = '" + idSave + "'";
                    scheduleHelper.QueryData(query);
                    LocalDate temp = LocalDate.of(year,month,day);
                    scheduleNotification(day,month, year, idSave);
                    idSave = "";

                    finish();
                }

                //scheduleEventAdapter.notifyItemRangeChanged();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setSchedule(){
        Log.i("Cccc", idSave);
        if (!idSave.isEmpty()){
            String query = "SELECT * FROM schedule WHERE ID = '" + idSave + "'";
            Cursor cursor = scheduleHelper.GetData(query);

            Log.i("ABa", idSave);

            if (cursor.getCount() > 0){
                while (cursor.moveToNext()) {
                    //id = String.format("%05d", Integer.parseInt(cursor.getString(0)));
                    titleEventText.setText(cursor.getString(1));
                    detailEventText.setText(cursor.getString(2));
                    locationEventText.setText(cursor.getString(3));
                    Log.i("Aaa", idSave);
                    dateButton.setText(Integer.toString(cursor.getInt(4)) + " Tháng " + Integer.toString(cursor.getInt(5)) + " " + Integer.toString(cursor.getInt(6)));
                    String time = (cursor.getInt(7) < 10 ? "0" : "") + Integer.toString(cursor.getInt(7)) + ":" + ((cursor.getInt(8) % 60) < 10 ? "0" : "") + Integer.toString(cursor.getInt(8) % 60);
                    hours = cursor.getInt(7);
                    totalMinutes = cursor.getInt(8);
                    ((Button)findViewById(R.id.timeEventTV)).setText(time);
                }
            }
        } else {
            titleEventText.setText("");
            detailEventText.setText("");
            locationEventText.setText("");

            dateButton.setText(getTodaysDate());
            ((Button)findViewById(R.id.timeEventTV)).setText(getNowTime());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
//        year = cal.get(Calendar.YEAR);
//        month = cal.get(Calendar.MONTH);
//        month = month + 1;
//        day = cal.get(Calendar.DAY_OF_MONTH);
        year = CalendarUtils.selectedDate.getYear();
        month = CalendarUtils.selectedDate.getMonthValue();
        day = CalendarUtils.selectedDate.getDayOfMonth();
        return makeDateString(day, month, year);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getNowTime(){
        hours = LocalTime.now().getHour();
        totalMinutes = hours * 60 + LocalTime.now().getMinute();
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

                ScheduleEventSetting.day = day;
                ScheduleEventSetting.month = month;
                ScheduleEventSetting.year = year;
            }
        };

        Calendar cal = Calendar.getInstance();
        year = CalendarUtils.selectedDate.getYear();
        month = CalendarUtils.selectedDate.getMonthValue();
        day = CalendarUtils.selectedDate.getDayOfMonth();

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month - 1, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        //Time Picker
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ((Button)findViewById(R.id.timeEventTV)).setText((hourOfDay < 10? "0":"") + hourOfDay + ":" + (minute < 10? "0":"") + minute);

                ScheduleEventSetting.hours = hourOfDay;
                ScheduleEventSetting.totalMinutes = hourOfDay * 60 + minute;
            }
        };

        LocalTime time = LocalTime.now();
        hours = time.getHour();
        totalMinutes = hours * 60 + time.getMinute();
        timePickerDialog = new TimePickerDialog(this, timeSetListener, time.getHour(), time.getMinute(), true);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }
    private void scheduleNotification(int ngay, int thang, int nam, String id) {
        // Tạo Calendar để lên lịch vào 12:00 AM hàng ngày
        Log.i("noti", "TRUE");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, nam);
        calendar.set(Calendar.MONTH, thang - 1);
        calendar.set(Calendar.DAY_OF_MONTH, ngay);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, totalMinutes % 60);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


//        // Nếu thời gian đã qua 12:00 AM hôm nay, lên lịch vào ngày mai
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

//         Intent để gửi tới BroadcastReceiver
        Intent intent = new Intent(getBaseContext(), ScheduleReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE);

        Log.i("receiveID", id);
       intent.putExtra("id", id);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

//         Lấy AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
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
        if (!idSave.isEmpty())
            idSave = "";
    }

    public void openEventDatePicker(View view) {
        datePickerDialog.show();
    }

    public void openTimePicker(View view) {
        timePickerDialog.show();
    }
}