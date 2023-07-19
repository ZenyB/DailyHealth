package com.example.dailyhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HorizontalExerciseAdapter extends RecyclerView.Adapter<HorizontalExerciseAdapter.ViewHolder>{
    Activity activity;
    ArrayList<MainExercise> mainExercises;
    private HorizontalExerciseAdapter.OnItemClick onItemClick;


    public HorizontalExerciseAdapter(Activity activity, ArrayList<MainExercise> mainExercises, HorizontalExerciseAdapter.OnItemClick onItemClick) {
        this.activity = activity;
        this.mainExercises = mainExercises;
        this.onItemClick = onItemClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HorizontalExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_exercise_item, parent, false);
        return new HorizontalExerciseAdapter.ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalExerciseAdapter.ViewHolder holder, int position) {
        MainExercise mainExercise = mainExercises.get(position);

        holder.nameTextView.setText(mainExercise.getName());

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
        TextView nameTextView;
        private ItemClickListener itemClickListener;
        private HorizontalExerciseAdapter.OnItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, HorizontalExerciseAdapter.OnItemClick onItemClick) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
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
            onItemClick.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public interface OnItemClick {
        void onClick(int position);
    }
}
