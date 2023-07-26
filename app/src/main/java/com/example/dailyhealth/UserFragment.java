package com.example.dailyhealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.dailyhealth.database.MoonHelper;
import com.example.dailyhealth.database.UserHelper;
import com.example.dailyhealth.database.WeekInfoHelper;

import java.time.LocalDate;

public class UserFragment extends Fragment {
    public String databaseName = "DAILYHEATH";
    private TextView recent ;
    private TextView predict ;
    private TextView te1 ;
    private TextView te2 ;
    private TextView userName, sleepAve,waterAve,exerciseAve, sleepToday, waterToday, exerciseToday;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        sleepAve = view.findViewById(R.id.SleepAve);
        waterAve = view.findViewById(R.id.WaterAve);
        exerciseAve = view.findViewById(R.id.ExerciseAve);
        sleepToday = view.findViewById(R.id.SleepTime);
        waterToday = view.findViewById(R.id.Water);
        exerciseToday = view.findViewById(R.id.ExerciseTime);
        userName = view.findViewById(R.id.UserName);
        recent = view.findViewById(R.id.LastMes);
        predict = view.findViewById(R.id.preMes);
        te1 = view.findViewById(R.id.tex1);
        te2 = view.findViewById(R.id.tex2);
        setValue();

        LinearLayout sleepLayout = view.findViewById(R.id.sleepLayout);
        sleepLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SleepStatistics.class);
                startActivity(intent);
            }
        });
        LinearLayout exerciseLayout = view.findViewById(R.id.exerciseLayout);
        exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExerciseStatistics.class);
                startActivity(intent);
            }
        });
        LinearLayout waterLayout = view.findViewById(R.id.waterLayout);
        waterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WaterStatistics.class);
                startActivity(intent);
            }
        });
        LinearLayout menLayout = view.findViewById(R.id.menLayout);
        menLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                SQLiteDatabase db = getActivity().openOrCreateDatabase("DAILYHEATH", Context.MODE_PRIVATE, null);
                if (SplashScreen.isTableExist(db, "MOON")) {
                    intent = new Intent(getActivity(), MoonCalendar.class);
                }
                else
                    intent = new Intent(getActivity(), MoonSettingScreen1.class);
                startActivity(intent);
            }
        });

        return view;
        //SQLiteDatabase db = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        setValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setValue(){
        //exerciseAve = find

        Double average_water = null, average_sleep = null,average_exercise = null;
        String collum = "TAPLUYEN";
        WeekInfoHelper weekInfoHelper = new WeekInfoHelper(getActivity());
        String query = "SELECT AVG(" + collum + ") FROM weekInfo WHERE SHOWABLE = 1";
        Cursor cursor = weekInfoHelper.GetData(query);
        if (cursor.moveToFirst()) {
            average_exercise = cursor.getDouble(0);
        }
        cursor.close();
        collum = "GIONGU";
        query = "SELECT AVG(" + collum + ") FROM weekInfo WHERE SHOWABLE = 1";
        cursor = weekInfoHelper.GetData(query);
        if (cursor.moveToFirst()) {
            average_sleep = cursor.getDouble(0);
        }
        cursor.close();
        collum = "LUONGNUOC";
        query = "SELECT AVG(" + collum + ") FROM weekInfo WHERE SHOWABLE = 1";
        cursor = weekInfoHelper.GetData(query);
        if (cursor.moveToFirst()) {
            average_water = cursor.getDouble(0);
        }
        cursor.close();

        if (average_exercise != null && average_sleep!= null & average_water!= null){
            sleepAve.setText(convertToHourMinuteFormat(average_sleep));
            waterAve.setText(average_water.toString() + "ml");
            exerciseAve.setText(convertToHourMinuteFormat(average_exercise));
        }

        UserHelper userHelper = new UserHelper(getActivity());
        String query_temp = "SELECT * FROM users";
        cursor = userHelper.GetData(query_temp);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                userName.setText(" " + name);
                int luongnuochomnay = cursor.getInt(6);

                String nuoc = Integer.toString(luongnuochomnay) + "ml";
                int gionguhomnay = cursor.getInt(7);
                int luyentaphomnay = cursor.getInt(8);
                waterToday.setText(nuoc);
                sleepToday.setText(convertToHourMinuteFormat(gionguhomnay));
                exerciseToday.setText(convertToHourMinuteFormat(luyentaphomnay));

            }
        }
        // Moon

        MoonHelper moonHelper = new MoonHelper(getActivity());
        SQLiteDatabase db = getActivity().openOrCreateDatabase("DAILYHEATH", Context.MODE_PRIVATE, null);
        if (!SplashScreen.isTableExist(db, "MOON")){
            te1.setVisibility(View.INVISIBLE);
            recent.setVisibility(View.INVISIBLE);
            predict.setVisibility(View.INVISIBLE);
            te2.setText("Nhấn để sử dụng chức năng");
        }
        else
        {
            query = "SELECT NGAYBATDAU ,THANGBATDAU , NAMBATDAU , TRUNGBINHCHUKY FROM MOON WHERE ID = '1'";
            Cursor cursor1 = moonHelper.GetData(query);
            if (cursor1.getCount()>0){
                while (cursor1.moveToNext()){
                    int ngay = cursor1.getInt(0);
                    int thang = cursor1.getInt(1);
                    int nam = cursor1.getInt(2);
                    int chuky = cursor1.getInt(3);
                    recent.setText(ngay + " / " + thang + " / "+nam);
                    LocalDate pre;
                    pre = LocalDate.of(nam,thang,ngay);
                    LocalDate newday = pre.plusDays(chuky);
                    ngay = newday.getDayOfMonth();
                    thang = newday.getMonthValue();
                    nam = newday.getYear();
                    predict.setText(ngay + " / " + thang + " / "+nam);
                }
            }
        }

    }
    private String convertToHourMinuteFormat(Double value) {
        int hours = (int) (value / 60);
        int minutes = (int) (value % 60);
        return String.format("%dh%02dp", hours, minutes);
    }
    private String convertToHourMinuteFormat(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%dh%02dp", hours, minutes);
    }
}
