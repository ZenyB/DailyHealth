package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    Activity activity;
    ArrayList<Integer> mainExercises;
    private OnSettingItemClick onItemClick;

    public SettingAdapter(Activity activity, ArrayList<Integer> mainExercises, OnSettingItemClick onItemClick) {
        this.activity = activity;
        this.mainExercises = mainExercises;
        this.onItemClick = onItemClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SettingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setting_item, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingAdapter.ViewHolder holder, int position) {
        Integer mainExercise = mainExercises.get(position);

        switch (mainExercise) {
            case 0:
                holder.nameTextView.setText(R.string.first_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_alarm);
                break;
            case 1:
                holder.nameTextView.setText(R.string.second_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_sleep);
                break;
            case 2:
                holder.nameTextView.setText(R.string.third_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_glass);
                break;
            case 3:
                holder.nameTextView.setText(R.string.fourth_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_calendar);
                break;
            case 4:
                holder.nameTextView.setText(R.string.fifth_item_setting_title);
                holder.imageView.setBackgroundResource(R.mipmap.ic_person);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mainExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView nameTextView;
        ImageView imageView;
        private ItemClickListener itemClickListener;
        private OnSettingItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, OnSettingItemClick onItemClick) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.imageView);
            this.onItemClick = onItemClick;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            itemClickListener.onClick(v,getAdapterPosition(),false);
            onItemClick.onSettingClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public interface OnSettingItemClick {
        void onSettingClick(int position);
    }
}
