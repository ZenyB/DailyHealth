package com.example.dailyhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainExerciseAdapter extends RecyclerView.Adapter<MainExerciseAdapter.ViewHolder> {
    Activity activity;
    ArrayList<MainExercise> mainExercises;

    public MainExerciseAdapter(Activity activity, ArrayList<MainExercise> mainExercises) {
        this.activity = activity;
        this.mainExercises = mainExercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_exercise_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainExerciseAdapter.ViewHolder holder, int position) {
        MainExercise mainExercise = mainExercises.get(position);

        holder.nameTextView.setText(mainExercise.getName());
        holder.timeTextView.setText(mainExercise.getTime());
        holder.kcalTextView.setText(mainExercise.getKcal());
    }

    @Override
    public int getItemCount() {
        return mainExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, kcalTextView, timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            kcalTextView = itemView.findViewById(R.id.kcalTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
