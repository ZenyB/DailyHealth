package com.example.dailyhealth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleEventAdapter extends RecyclerView.Adapter<ScheduleEventAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ScheduleEvent> scheduleEvents;
    private ScheduleEventAdapter.OnItemClick onItemClick;


    public ScheduleEventAdapter(Activity activity, ArrayList<ScheduleEvent> scheduleEvents, ScheduleEventAdapter.OnItemClick onItemClick) {
        this.activity = activity;
        this.scheduleEvents = scheduleEvents;
        this.onItemClick = onItemClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScheduleEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_event, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new ScheduleEventAdapter.ViewHolder(view, onItemClick);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ScheduleEventAdapter.ViewHolder holder, int position) {
        ScheduleEvent scheduleEvent = scheduleEvents.get(position);
        position = holder.getAdapterPosition();
        if(position % 2 != 0) {
            holder.constraintLayout.setBackgroundResource(R.drawable.rounded_white);
            holder.titleTextView.setTextColor(R.color.black);
            holder.detailTextView.setTextColor(R.color.black);
            holder.locationTextView.setTextColor(R.color.black);
            holder.imgView.setImageResource(R.drawable.location_img);
            holder.delBut.setBackgroundResource(R.drawable.ic_x_black_foreground);
        }
        else {
            holder.constraintLayout.setBackgroundResource(R.drawable.rounded_green);
            holder.titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.detailTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.locationTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.imgView.setImageResource(R.drawable.location_white);
            holder.delBut.setBackgroundResource(R.drawable.ic_x_white_foreground);
        }
        holder.titleTextView.setText(scheduleEvent.getTitle());
        holder.timeTextView.setText(scheduleEvent.getTime());
        holder.detailTextView.setText(scheduleEvent.getDetail());
        holder.locationTextView.setText(scheduleEvent.getLocation());
        int finalPosition = position;
        holder.delBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, Integer.toString(holder.getPosition()), Toast.LENGTH_SHORT).show();
                scheduleEvents.remove(holder.getPosition());
                notifyItemRemoved(holder.getPosition());
                notifyItemRangeChanged(holder.getPosition(), scheduleEvents.size() - holder.getPosition());
            }
        });


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
        return scheduleEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView titleTextView, detailTextView, timeTextView, locationTextView;
        private ItemClickListener itemClickListener;
        ConstraintLayout constraintLayout;
        Button delBut;
        ImageView imgView;
        private ScheduleEventAdapter.OnItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, ScheduleEventAdapter.OnItemClick onItemClick) {
            super(itemView);
            delBut = itemView.findViewById(R.id.scheduleEventDelete);
            constraintLayout = itemView.findViewById((R.id.scheduleEventBorder));
            titleTextView = itemView.findViewById(R.id.scheduleEventTitleTV);
            detailTextView = itemView.findViewById(R.id.scheduleEventDetailTV);
            timeTextView = itemView.findViewById(R.id.scheduleTimeTV);
            locationTextView = itemView.findViewById(R.id.scheduleEventLocationTV);
            imgView = itemView.findViewById(R.id.imageView3);

            this.onItemClick = onItemClick;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
//            itemClickListener.onClick(v,getAdapterPosition(),false);
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
