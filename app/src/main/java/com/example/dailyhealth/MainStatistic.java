package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainStatistic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_main);

        LinearLayout sleepLayout = findViewById(R.id.sleepLayout);
        sleepLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainStatistic.this, SleepStatistics.class);
                startActivity(intent);
            }
        });
        LinearLayout exerciseLayout = findViewById(R.id.exerciseLayout);
        exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainStatistic.this, ExerciseStatistics.class);
                startActivity(intent);
            }
        });
        LinearLayout waterLayout = findViewById(R.id.waterLayout);
        waterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainStatistic.this, WaterStatistics.class);
                startActivity(intent);
            }
        });

    }
}