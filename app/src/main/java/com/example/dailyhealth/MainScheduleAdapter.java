package com.example.dailyhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainScheduleAdapter extends RecyclerView.Adapter<MainScheduleAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ScheduleEvent> schedules;
    private MainScheduleAdapter.OnItemClick onItemClick;

    public MainScheduleAdapter(Activity activity, ArrayList<ScheduleEvent> schedules, MainScheduleAdapter.OnItemClick onItemClick) {
        this.activity = activity;
        this.schedules = schedules;
        this.onItemClick = onItemClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_schedule_item, parent, false);
        return new MainScheduleAdapter.ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MainScheduleAdapter.ViewHolder holder, int position) {
        ScheduleEvent schedule = schedules.get(position);
        if (position % 2 != 0)
            holder.constraintLayout.setBackgroundResource(R.drawable.purple_right);
        holder.titleTV.setText(schedule.getTitle() + " - ngày " + schedule.getDay() + "/" + schedule.getMonth() + "/" + schedule.getYear());

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView titleTV;
        ConstraintLayout constraintLayout;
        private ItemClickListener itemClickListener;
        private MainScheduleAdapter.OnItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, MainScheduleAdapter.OnItemClick onItemClick) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.scheduleTitleTV);
            constraintLayout = itemView.findViewById(R.id.constraint);
            this.onItemClick = onItemClick;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onScheduleClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public interface OnItemClick {
        void onScheduleClick(int position);
    }
}
