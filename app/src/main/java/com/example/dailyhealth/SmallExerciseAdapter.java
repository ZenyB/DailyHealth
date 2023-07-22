package com.example.dailyhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SmallExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity activity;
    ArrayList<SmallExercise> smallExercises;
    int index = 0;

    public SmallExerciseAdapter(Activity activity, ArrayList<SmallExercise> smallExercises) {
        this.activity = activity;
        this.smallExercises = smallExercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case 0:
                View view1 = inflater.inflate(R.layout.small_exercise_item, parent, false);
                viewHolder = new ViewHolder1(view1);
                break;
            case 1:
                View view2 = inflater.inflate(R.layout.small_exercise_item_activated, parent, false);
                viewHolder = new ViewHolderActivated(view2);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SmallExercise smallExercise = smallExercises.get(position);

        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder1 type1ViewHolder = (ViewHolder1) holder;
                type1ViewHolder.nameTextView.setText(smallExercise.getName());
                type1ViewHolder.timeTextView.setText(smallExercise.getDuration());
                break;
            case 1:
                ViewHolderActivated type2ViewHolder = (ViewHolderActivated) holder;
                type2ViewHolder.nameTextView.setText(smallExercise.getName());
                type2ViewHolder.timeTextView.setText(smallExercise.getDuration());
                type2ViewHolder.imageView.setImageResource(R.mipmap.ic_person);
                type2ViewHolder.pb.setProgress(50);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type: " + holder.getItemViewType());
        }
    }

    @Override
    public int getItemCount() {
        return smallExercises.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (index == position) {
            return 1;
        }
        return 0;
    }


    private class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView nameTextView, timeTextView;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }

    public class ViewHolderActivated extends RecyclerView.ViewHolder {
        TextView nameTextView, timeTextView;
        ImageView imageView;
        ImageButton imageButton;
        ProgressBar pb;
        public ViewHolderActivated(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            imageView = itemView.findViewById(R.id.imageView2);
            imageButton = itemView.findViewById(R.id.imageButton);
            pb = itemView.findViewById(R.id.progressBar);
        }

    }
}
