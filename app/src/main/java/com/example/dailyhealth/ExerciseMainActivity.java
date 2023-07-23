package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseMainActivity extends AppCompatActivity implements MainExerciseAdapter.OnItemClick {

    private ArrayList<MainExercise> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_main);

//        getSupportActionBar().hide();

        arrayList = JSONFileHandler.readMainExercisesFromJSON(this);

        final RecyclerView r1 = (RecyclerView) findViewById(R.id.suggestExerciseRecyclerView);
        r1.setLayoutManager(new LinearLayoutManager(this));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r1.setAdapter(new MainExerciseAdapter(this, arrayList, this));

        final RecyclerView r = (RecyclerView) findViewById(R.id.mainExerciseRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(this));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new MainExerciseAdapter(this, arrayList, this));
    }

    @Override
    public void onClick(int position) {
        MainExercise i = arrayList.get(position);
        Intent intent1 = new Intent(this, DetailExerciseActivity.class);
                    intent1.putExtra("Name", new String(i.getName()));
//                    intent1.putExtra("Name", new String(s.getName()));
//                    intent1.putExtra("Students", new Integer(s.getStudents()));
        startActivity(intent1);
    }
}