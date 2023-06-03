package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseMainActivity extends AppCompatActivity {

    private ArrayList<MainExercise> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_main);

        getSupportActionBar().hide();

        arrayList.add(new MainExercise("Bài tập chính", "5 phút", "200KCAL"));
        arrayList.add(new MainExercise("Bài tập cơ tay", "5 phút", "200KCAL"));

        final RecyclerView r = (RecyclerView) findViewById(R.id.mainExerciseRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new MainExerciseAdapter(this, arrayList));
    }
}