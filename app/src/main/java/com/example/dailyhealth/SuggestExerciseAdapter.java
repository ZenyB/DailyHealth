package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestExerciseAdapter extends RecyclerView.Adapter<SuggestExerciseAdapter.ViewHolder> {
    Activity activity;
    ArrayList<MainExercise> mainExercises;
    private OnSuggestItemClick onItemClick;


    public SuggestExerciseAdapter(Activity activity, ArrayList<MainExercise> mainExercises, OnSuggestItemClick onItemClick) {
        this.activity = activity;
        this.mainExercises = mainExercises;
        this.onItemClick = onItemClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SuggestExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_exercise_item, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestExerciseAdapter.ViewHolder holder, int position) {
        MainExercise mainExercise = mainExercises.get(position);

        holder.nameTextView.setText(mainExercise.getName());
        holder.timeTextView.setText(Integer.toString(mainExercise.getDuration()) + " phút");
        holder.kcalTextView.setText(mainExercise.getKcal());

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                if(isLongClick) {
//
//                }
//                else {
//
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mainExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView nameTextView, kcalTextView, timeTextView;
        private ItemClickListener itemClickListener;
        private OnSuggestItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, OnSuggestItemClick onItemClick) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            kcalTextView = itemView.findViewById(R.id.kcalTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            this.onItemClick = onItemClick;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

//        public void setItemClickListener(ItemClickListener itemClickListener)
//        {
//            this.itemClickListener = itemClickListener;
//        }

        @Override
        public void onClick(View v) {
//            itemClickListener.onClick(v,getAdapterPosition(),false);
            onItemClick.onSuggestClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public interface OnSuggestItemClick {
        void onSuggestClick(int position);
    }
}

