package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

public class GetTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started4);
        Button myButton = findViewById(R.id.doneClick);

        CheckBox mon, tue, wen, thu, fri, sat, sun, all;

        mon = findViewById(R.id.weekMon);
        tue = findViewById(R.id.weekTue);
        wen = findViewById(R.id.weekWen);
        thu = findViewById(R.id.weekThu);
        fri = findViewById(R.id.weekFri);
        sat = findViewById(R.id.weekSat);
        sun = findViewById(R.id.weekSun);
        all = findViewById(R.id.allWeek);

        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mon.setChecked(true);
                    tue.setChecked(true);
                    wen.setChecked(true);
                    thu.setChecked(true);
                    fri.setChecked(true);
                    sat.setChecked(true);
                    sun.setChecked(true);
                }
            }
        });

        mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                } else {
                    all.setChecked(false);
                }
            }
        });

        tue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                } else {
                    all.setChecked(false);
                }
            }
        });

        wen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                } else {
                    all.setChecked(false);
                }
            }
        });

        thu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                } else {
                    all.setChecked(false);
                }
            }
        });

        fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                } else {
                    all.setChecked(false);
                }
            }
        });

        sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                } else {
                    all.setChecked(false);
                }
            }
        });

        sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                } else {
                    all.setChecked(false);
                }
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetTime.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
    }
}
