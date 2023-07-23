package com.example.dailyhealth;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment implements HorizontalExerciseAdapter.OnItemClick, MainScheduleAdapter.OnItemClick {

    private ArrayList<MainExercise> arrayList = new ArrayList<>();
    private ArrayList<Schedule> schedules = new ArrayList<>();

    TextView drinkTV;

    String CHANNEL_ID = "MOON";

    private static final int NOTIFICATION_ALARM_REQUEST_CODE = 1001;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        arrayList = JSONFileHandler.readMainExercisesFromJSON(getActivity());

        final RecyclerView r = (RecyclerView) view.findViewById(R.id.exMainRV);
        r.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new HorizontalExerciseAdapter(getActivity(), arrayList, this));

        schedules.add(new Schedule("Đi du lịch - ngày 12/4/2023"));
        schedules.add(new Schedule("Đi đến phòng tập - ngày 15/7/2023"));

        final RecyclerView r1 = (RecyclerView) view.findViewById(R.id.scheduleMainRV);
        r1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r1.setAdapter(new MainScheduleAdapter(getActivity(), schedules, this));

        drinkTV = view.findViewById(R.id.amountWaterTV);
        drinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //Navigate qua màn hình uống nước
//                Toast.makeText(getActivity(), "Navigate to Uống nước", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SleepStatistics.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
                createNotificationChannel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.icon_sleep)
                        .setContentTitle("Sleep")
                        .setContentText("You have to sleep more")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

                // notificationId is a unique int for each notification that you must define
                int notificationId = (int) System.currentTimeMillis();
                notificationManager.notify(notificationId, builder.build());

                builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.icon_sleep)
                        .setContentTitle("Drink")
                        .setContentText("You have to drink more water")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setAutoCancel(true);
                notificationManager.notify(notificationId, builder.build());
            }
        });
        //scheduleNotification();
//        getActivity().registerReceiver(new TestReceiver(), new IntentFilter());
        return view;
    }

    @Override
    public void onClick(int position) {
        MainExercise i = arrayList.get(position);
        Intent intent1 = new Intent(getActivity(), DetailExerciseActivity.class);
        intent1.putExtra("position", position);
        intent1.putExtra("type", "suggest");
//                    intent1.putExtra("Name", new String(s.getName()));
//                    intent1.putExtra("Students", new Integer(s.getStudents()));
        startActivity(intent1);
    }

    @Override
    public void onScheduleClick(int position) {
        MainExercise i = arrayList.get(position);
        //Navigate qua màn hình schedule
//        Intent intent1 = new Intent(this, ExerciseMainActivity.class);
////        intent1.putExtra("Title", new String(i.getName()));
////                    intent1.putExtra("Name", new String(s.getName()));
////                    intent1.putExtra("Students", new Integer(s.getStudents()));
//        startActivity(intent1);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //String CHANNEL_ID = "MOON";
            CharSequence name = getString(R.string.moon_channel_name);
            String description = getString(R.string.moon_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    private void scheduleNotification() {
//        // Tạo Calendar để lên lịch vào 12:00 AM hàng ngày
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.YEAR, 2023);
//        calendar.set(Calendar.MONTH, Calendar.JULY);
//        calendar.set(Calendar.DAY_OF_MONTH, 21);
//        calendar.set(Calendar.HOUR_OF_DAY, 8);
//        calendar.set(Calendar.MINUTE, 20);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
////        // Nếu thời gian đã qua 12:00 AM hôm nay, lên lịch vào ngày mai
//        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
////         Intent để gửi tới BroadcastReceiver
//        Intent intent = new Intent(getActivity(), TestReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
////         Lấy AlarmManager
//        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//    }
}
