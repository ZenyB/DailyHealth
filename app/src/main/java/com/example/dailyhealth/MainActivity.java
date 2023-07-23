package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HorizontalExerciseAdapter.OnItemClick, MainScheduleAdapter.OnItemClick{

    private ArrayList<MainExercise> arrayList = new ArrayList<>();
    private ArrayList<Schedule> schedules = new ArrayList<>();
//    private ArrayList<BottleInfo> bottleInfos = new ArrayList<>();

    TextView drinkTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = JSONFileHandler.readMainExercisesFromJSON(this);

        final RecyclerView r = (RecyclerView) findViewById(R.id.exMainRV);
        r.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new HorizontalExerciseAdapter(this, arrayList, this));

        schedules.add(new Schedule("Đi du lịch - ngày 12/4/2023"));
        schedules.add(new Schedule("Đi đến phòng tập - ngày 15/7/2023"));

        final RecyclerView r1 = (RecyclerView) findViewById(R.id.scheduleMainRV);
        r1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r1.setAdapter(new MainScheduleAdapter(this, schedules, this));

        drinkTV = findViewById(R.id.amountWaterTV);
        drinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate qua màn hình uống nước
                Toast.makeText(getApplicationContext(), "Navigate to Uống nước", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(int position) {
        MainExercise i = arrayList.get(position);
        Intent intent1 = new Intent(MainActivity.this, DetailExerciseActivity.class);
        intent1.putExtra("Name", new String(i.getName()));
//                    intent1.putExtra("Name", new String(s.getName()));
//                    intent1.putExtra("Students", new Integer(s.getStudents()));
        startActivity(intent1);
    }

    @Override
    public void onScheduleClick(int position) {
        MainExercise i = arrayList.get(position);
        //Navigate qua màn hình schedule
//        Intent intent1 = new Intent(this, ExerciseMainActivity.class);
////        intent1.putExtra("Title", new String(i.getName()));
////                    intent1.putExtra("Name", new String(s.getName()));
////                    intent1.putExtra("Students", new Integer(s.getStudents()));
//        startActivity(intent1);
    }
}