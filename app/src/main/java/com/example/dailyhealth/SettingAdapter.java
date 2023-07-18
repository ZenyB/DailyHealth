package com.example.dailyhealth;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    Activity activity;
    ArrayList<Integer> mainExercises;

    public SettingAdapter(Activity activity, ArrayList<Integer> mainExercises) {
        this.activity = activity;
        this.mainExercises = mainExercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setting_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.ViewHolder holder, int position) {
        Integer mainExercise = mainExercises.get(position);

        switch (mainExercise) {
            case 0: holder.nameTextView.setText(R.string.first_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_alarm);
                break;
            case 1: holder.nameTextView.setText(R.string.second_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_sleep);
                break;
            case 2: holder.nameTextView.setText(R.string.third_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_glass);
                break;
            case 3: holder.nameTextView.setText(R.string.fourth_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_calendar);
                break;
            case 4: holder.nameTextView.setText(R.string.fifth_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_person);
                break;
        }
        //holder.nameTextView.setText(mainExercise.getName());
//        holder.timeTextView.setText(mainExercise.getTime());
//        holder.kcalTextView.setText(mainExercise.getKcal());
    }

    @Override
    public int getItemCount() {
        return mainExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
