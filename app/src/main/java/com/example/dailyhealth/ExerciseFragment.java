package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseFragment extends Fragment implements MainExerciseAdapter.OnItemClick {

    private ArrayList<MainExercise> arrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        arrayList.add(new MainExercise("Bài tập chính", "5 phút", "200KCAL"));
        arrayList.add(new MainExercise("Bài tập cơ tay", "5 phút", "200KCAL"));

        final RecyclerView r1 = (RecyclerView) view.findViewById(R.id.suggestExerciseRecyclerView);
        r1.setLayoutManager(new LinearLayoutManager(getActivity()));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r1.setAdapter(new MainExerciseAdapter(getActivity(), arrayList, this));

        final RecyclerView r = (RecyclerView) view.findViewById(R.id.mainExerciseRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(getActivity()));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new MainExerciseAdapter(getActivity(), arrayList, this));
        return view;
    }

    @Override
    public void onClick(int position) {
        MainExercise i = arrayList.get(position);
        Intent intent1 = new Intent(getActivity(), DetailExerciseActivity.class);
        intent1.putExtra("Name", new String(i.getName()));
//                    intent1.putExtra("Name", new String(s.getName()));
//                    intent1.putExtra("Students", new Integer(s.getStudents()));
        startActivity(intent1);
    }
}
