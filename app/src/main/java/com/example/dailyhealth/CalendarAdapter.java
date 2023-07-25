package com.example.dailyhealth;
//
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.time.YearMonth;
//import java.util.ArrayList;
//
//class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
//{
//    private final ArrayList<String> daysOfMonth;
//    private final OnItemListener onItemListener;
//
//    private final ArrayList<String> highlightedDays;
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public CalendarAdapter(ArrayList<String> highlightedDays, OnItemListener onItemListener)
//    {
//        this.daysOfMonth = daysInMonthArray(LocalDate.now());
//        this.onItemListener = onItemListener;
//        this.highlightedDays = highlightedDays;
//    }
//
//    @NonNull
//    @Override
//    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//    {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = (int) (parent.getWidth() / 7);
//        return new CalendarViewHolder(view, onItemListener);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
//    {
//        holder.dayOfMonth.setText(daysOfMonth.get(position));
//        if(highlightedDays.contains(daysOfMonth.get(position)))
//            holder.dayOfMonth.setBackgroundResource(R.drawable.circle);
//
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        return daysOfMonth.size();
//    }
//
//    public interface  OnItemListener
//    {
//        void onItemClick(int position, String dayText);
//    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private ArrayList<String> daysInMonthArray(LocalDate date)
//    {
//        ArrayList<String> daysInMonthArray = new ArrayList<>();
//        YearMonth yearMonth = YearMonth.from(date);
//
//        int daysInMonth = yearMonth.lengthOfMonth();
//
//        LocalDate firstOfMonth = date.withDayOfMonth(1);
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
//}

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyhealth.database.MoonHelper;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private static MoonHelper moonHelper;
    private static boolean index;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(parent.getRootView().findViewById(R.id.moonStartButton) != null)
            index = true;
        else
            index = false;
        Log.i("indexsdads", Boolean.toString(index));
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) parent.getWidth() / 7;

        moonHelper = new MoonHelper(parent.getContext());
        String query = "SELECT NGAYBATDAU, THANGBATDAU, NAMBATDAU, TRUNGBINHCHUKY, TRUNGBINHKINHNGUYET, THOIGIANNHACTRUOC, NGAY, THANG, NAM, HANHKINH FROM MOON WHERE ID = '1'";
        Cursor cursor = moonHelper.GetData(query);
        if(cursor.getCount() > 0)
        {
            while (cursor.moveToNext()){
                Integer ngaybatdau = cursor.getInt(0);
                Integer thangbatdau = cursor.getInt(1);
                Integer nambatdau = cursor.getInt(2);
                Integer tbchuky = cursor.getInt(3);
                Integer tbkinhnguyet = cursor.getInt(4);
                Integer thoigiannhac = cursor.getInt(5);
                Integer ngay = cursor.getInt(6);
                Integer thang = cursor.getInt(7);
                Integer nam = cursor.getInt(8);
                Integer hanhkinh = cursor.getInt(9);
                CalendarUtils.startDate = LocalDate.of(nambatdau, thangbatdau , ngaybatdau);
                if(hanhkinh == 1)
                    CalendarUtils.startButtonDate = LocalDate.of(nam, thang , ngay);
                CalendarUtils.moonDays = tbkinhnguyet;
                CalendarUtils.cycleDays = tbchuky;
            }
        }

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        Log.i("aa",holder.parentView.getRootView().toString());

        final ArrayList<LocalDate> daysMooning = CalendarUtils.daysMooningArray();
        final ArrayList<LocalDate> daysInMoon = CalendarUtils.daysInMoonArray();
        final LocalDate date = days.get(position);
        if(date == null)
            holder.dayOfMonth.setText("");
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            else if(daysMooning.contains(date) && index)
                holder.parentView.setBackgroundColor(Color.parseColor("#F33058"));
            else if(daysInMoon.contains(date) && index)
                holder.parentView.setBackgroundColor(Color.parseColor("#F598E2"));
        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}