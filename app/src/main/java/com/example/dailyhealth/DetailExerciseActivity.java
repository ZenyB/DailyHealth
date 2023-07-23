package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailExerciseActivity extends AppCompatActivity {

    private ArrayList<SmallExercise> arrayList = new ArrayList<>();
    ImageButton imgButton;
    public static ImageButton startBtn;
    public static TextView relaxTV;
    TextView mainNameTV, mainTimeTV;

    private int positionNow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_exercise);

        int position = getIntent().getIntExtra("position", 0);
        mainNameTV = findViewById(R.id.exerciseNameTextView);
        mainTimeTV = findViewById(R.id.timeTextView);
        startBtn = findViewById(R.id.btnPlayPause);
        relaxTV = findViewById(R.id.relaxTimeTV);

        if (getIntent().getStringExtra("type").equals("all")) {
            mainNameTV.setText(ExerciseFragment.allMainExercise.get(position).getName());
            mainTimeTV.setText(Integer.toString(ExerciseFragment.allMainExercise.get(position).getDuration()) + " phút");
            arrayList = ExerciseFragment.allMainExercise.get(position).getSmallExercises();
        } else {
            arrayList = ExerciseFragment.suggestMainExercises.get(position).getSmallExercises();
            mainNameTV.setText(ExerciseFragment.suggestMainExercises.get(position).getName());
            mainTimeTV.setText(Integer.toString(ExerciseFragment.suggestMainExercises.get(position).getDuration()) + " phút");
        }

        final RecyclerView r = (RecyclerView) findViewById(R.id.detailExerciseRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new SmallExerciseAdapter(this, arrayList));

        imgButton=findViewById(R.id.btnBack);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExerciseActivity.super.finish();
            }
        });
    }
}