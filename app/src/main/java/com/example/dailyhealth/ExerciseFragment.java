package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyhealth.database.UserHelper;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment implements MainExerciseAdapter.OnItemClick, SuggestExerciseAdapter.OnSuggestItemClick {

    public static ArrayList<MainExercise> suggestMainExercises = new ArrayList<>();
    public static ArrayList<MainExercise> allMainExercise = new ArrayList<>();
    private UserHelper userHelper = new UserHelper(getActivity());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        allMainExercise = JSONFileHandler.readMainExercisesFromJSON(getActivity());
        //Tính toán BMI các kiểu
//        String query = "SELECT * FROM USER";
//        Cursor cursor = userHelper.GetData(query);
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                Integer chieucao = cursor.getInt(4);
//                Integer cannang = cursor.getInt(5);
//                Float BMI = Float.valueOf(cannang / ((chieucao / 100) * (chieucao / 100)));
//            }
//        }



        final RecyclerView r1 = (RecyclerView) view.findViewById(R.id.suggestExerciseRecyclerView);
        r1.setLayoutManager(new LinearLayoutManager(getActivity()));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r1.setAdapter(new MainExerciseAdapter(getActivity(), suggestMainExercises, this));

        final RecyclerView r = (RecyclerView) view.findViewById(R.id.mainExerciseRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(getActivity()));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new MainExerciseAdapter(getActivity(), allMainExercise, this));
        return view;
    }

    @Override
    public void onClick(int position) {
        MainExercise i = allMainExercise.get(position);
        Intent intent1 = new Intent(getActivity(), DetailExerciseActivity.class);
        intent1.putExtra("position", position);
        intent1.putExtra("type", "all");
//                    intent1.putExtra("Name", new String(s.getName()));
//                    intent1.putExtra("Students", new Integer(s.getStudents()));
        startActivity(intent1);
    }

    @Override
    public void onSuggestClick(int position) {
        MainExercise i = suggestMainExercises.get(position);
        Intent intent1 = new Intent(getActivity(), DetailExerciseActivity.class);
        intent1.putExtra("position", position);
        intent1.putExtra("type", "suggest");
//                    intent1.putExtra("Name", new String(s.getName()));
//                    intent1.putExtra("Students", new Integer(s.getStudents()));
        startActivity(intent1);
    }
}
