package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailExerciseActivity extends AppCompatActivity {

    private ArrayList<SmallExercise> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_exercise);

        getSupportActionBar().hide();

        arrayList.add(new SmallExercise("Bài tập chính", "5 phút"));
        arrayList.add(new SmallExercise("Bài tập cơ tay", "5 phút"));

        final RecyclerView r = (RecyclerView) findViewById(R.id.detailExerciseRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new SmallExerciseAdapter(this, arrayList));

        String s = getIntent().getExtras().getString("Name");
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}