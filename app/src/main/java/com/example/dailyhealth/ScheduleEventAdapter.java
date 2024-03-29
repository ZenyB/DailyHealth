package com.example.dailyhealth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyhealth.database.ScheduleHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleEventAdapter extends RecyclerView.Adapter<ScheduleEventAdapter.ViewHolder> {
    Activity activity;
    ArrayList<ScheduleEvent> scheduleEvents;
    private ScheduleEventAdapter.OnItemClick onItemClick;
    private Context context ;


    public ScheduleEventAdapter(Activity activity, ArrayList<ScheduleEvent> scheduleEvents, ScheduleEventAdapter.OnItemClick onItemClick) {
        this.activity = activity;
        this.scheduleEvents = scheduleEvents;
        this.onItemClick = onItemClick;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScheduleEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_event, parent, false);
        context = parent.getRootView().getContext();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new ScheduleEventAdapter.ViewHolder(view, onItemClick);
    }

    /*private void updateSchedule(){
        String query = "SELECT * FROM schedule";
        ScheduleHelper scheduleHelper = new ScheduleHelper(context);
        Cursor cursor = scheduleHelper.GetData(query);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Log.i("aaaaaa", cursor.getString(0));
                if (CalendarUtils.selectedDate.getDayOfMonth() == cursor.getInt(4) && cursor.getInt(5) == CalendarUtils.selectedDate.getMonthValue() && cursor.getInt(6) == CalendarUtils.selectedDate.getYear()){
                    scheduleEvents.add(new ScheduleEvent(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getInt(8)));
                }
            }
        }
    }*/

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
        String id = scheduleEvent.getId();
        holder.delBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                Toast.makeText(activity, Integer.toString(holder.getPosition()), Toast.LENGTH_SHORT).show();
                scheduleEvents.remove(holder.getPosition());
                String query = "DELETE FROM schedule WHERE ID = '" + id + "'";
                ScheduleHelper scheduleHelper = new ScheduleHelper(v.getContext());
                scheduleHelper.QueryData(query);
                notifyItemRemoved(holder.getPosition());
                notifyItemRangeChanged(holder.getPosition(), scheduleEvents.size() - holder.getPosition());
//                // Lấy danh sách các thông báo đã đăng ký trong NotificationManagerCompat
//                NotificationManager notificationManager = NotificationManagerCompat.from(v.getContext());
//                Notification[] activeNotifications = notificationManager.getActiveNotifications();
//
//// ID của thông báo cần kiểm tra
//                int notificationIdToCheck = YOUR_NOTIFICATION_ID;
//
//// Kiểm tra xem thông báo có tồn tại không
//                boolean isNotificationActive = false;
//
//                for (Notification notification : activeNotifications) {
//                    if (notification.getId() == notificationIdToCheck) {
//                        isNotificationActive = true;
//                        break;
//                    }
//                }
//
//// Kiểm tra kết quả
//                if (isNotificationActive) {
//                    // Thông báo cụ thể có tồn tại trong NotificationManagerCompat
//                    // Thực hiện các hành động bạn muốn tại đây
//                } else {
//                    // Thông báo cụ thể không tồn tại trong NotificationManagerCompat
//                    // Thực hiện các hành động khác nếu cần thiết
//                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis() + 5000);
//                calendar.add(Calendar.SECOND, 5);
                Log.i("CALENDAR", calendar.toString());
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(Integer.parseInt(id));
//                Intent intent = new Intent(v.getContext(),ScheduleReceiver.class);
//                intent.putExtra("isDelete",true);
//                intent.setAction("is_delete");
//
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(v.getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                // Cài đặt thông báo
//                AlarmManager alarmManager = (AlarmManager) v.getContext().getSystemService(Context.ALARM_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                } else {
//                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                }
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
