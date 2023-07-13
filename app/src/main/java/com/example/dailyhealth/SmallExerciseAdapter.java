package com.example.dailyhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SmallExerciseAdapter extends RecyclerView.Adapter<SmallExerciseAdapter.ViewHolder> {
    Activity activity;
    ArrayList<SmallExercise> smallExercises;

    public SmallExerciseAdapter(Activity activity, ArrayList<SmallExercise> smallExercises) {
        this.activity = activity;
        this.smallExercises = smallExercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SmallExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.small_exercise_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallExerciseAdapter.ViewHolder holder, int position) {
        SmallExercise smallExercise = smallExercises.get(position);

        holder.nameTextView.setText(smallExercise.getName());
        holder.timeTextView.setText(smallExercise.getTime());
    }

    @Override
    public int getItemCount() {
        return smallExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
