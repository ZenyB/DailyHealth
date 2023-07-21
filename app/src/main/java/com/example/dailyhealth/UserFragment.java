package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        LinearLayout sleepLayout = view.findViewById(R.id.sleepLayout);
        sleepLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SleepStatistics.class);
                startActivity(intent);
            }
        });
        LinearLayout exerciseLayout = view.findViewById(R.id.exerciseLayout);
        exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExerciseStatistics.class);
                startActivity(intent);
            }
        });
        LinearLayout waterLayout = view.findViewById(R.id.waterLayout);
        waterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WaterStatistics.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
