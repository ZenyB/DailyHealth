package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private ArrayList<Integer> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().hide();

        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);



        final RecyclerView r = (RecyclerView) findViewById(R.id.settingRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(this));
//        //Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new SettingAdapter(this, arrayList));
    }
}