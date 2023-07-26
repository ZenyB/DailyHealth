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
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyhealth.database.UserHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment implements HorizontalExerciseAdapter.OnItemClick, MainScheduleAdapter.OnItemClick {

    public static ArrayList<MainExercise> arrayList = new ArrayList<>();
    public static ArrayList<MainExercise> suggestArrayList = new ArrayList<>();
    public static ArrayList<ScheduleEvent> schedules = new ArrayList<>();
    private static TextView nameTV;
    private static UserHelper userHelper;
//    private static ScheduleHeper scheduleHeper;
    private static ProgressBar sleepPB, drinkPB, exercisePB, allPB;
    private static TextView sleepPercentTV, drinkPercentTV, exercisePercentTV, allGoalPercentTV;

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

        sleepPB = view.findViewById(R.id.sleepProgress);
        drinkPB = view.findViewById(R.id.waterProgress);
        exercisePB = view.findViewById(R.id.exerciseProgress);
        allPB = view.findViewById(R.id.allGoalProgress);

        sleepPercentTV = view.findViewById(R.id.sleepPercentTV);
        drinkPercentTV = view.findViewById(R.id.waterPercentTV);
        exercisePercentTV = view.findViewById(R.id.exercisePercentTV);
        allGoalPercentTV = view.findViewById(R.id.allGoalPercentTV);

        nameTV = view.findViewById(R.id.helloNameTV);

        userHelper = new UserHelper(getActivity().getApplicationContext());

        arrayList = JSONFileHandler.readMainExercisesFromJSON(getActivity());
        String query = "SELECT CHIEUCAO, CANNANG FROM USERS";
        Cursor cursor = userHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Integer chieucao = cursor.getInt(0);
                Integer cannang = cursor.getInt(1);
                getSuggestExerciseBaseOnBMI(chieucao, cannang);
            }
        }

        setProgressBarDaily();
        resetName();
        setSchedules();

        final RecyclerView r = (RecyclerView) view.findViewById(R.id.exMainRV);
        r.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r.setAdapter(new HorizontalExerciseAdapter(getActivity(), suggestArrayList, this));

        final RecyclerView r1 = (RecyclerView) view.findViewById(R.id.scheduleMainRV);
        r1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        r1.setAdapter(new MainScheduleAdapter(getActivity(), schedules, this));

        drinkTV = view.findViewById(R.id.amountWaterTV);
        drinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //Navigate qua màn hình uống nước
//                Toast.makeText(getActivity(), "Navigate to Uống nước", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WaterDaily.class);
                startActivity(intent);
//                Intent intent = new Intent(getActivity(), SleepStatistics.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                createNotificationChannel();
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
//                        .setSmallIcon(R.drawable.icon_sleep)
//                        .setContentTitle("Sleep")
//                        .setContentText("You have to sleep more")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
//                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                        .setAutoCancel(true);
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
//
//                // notificationId is a unique int for each notification that you must define
//                int notificationId = (int) System.currentTimeMillis();
//                notificationManager.notify(notificationId, builder.build());
//
//                builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
//                        .setSmallIcon(R.drawable.icon_sleep)
//                        .setContentTitle("Drink")
//                        .setContentText("You have to drink more water")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
//                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                        .setAutoCancel(true);
//                notificationManager.notify(notificationId, builder.build());
//            }
//        });
                //scheduleNotification();
//        getActivity().registerReceiver(new TestReceiver(), new IntentFilter());
//        return view;
            }
        });
        return view;
    }

    public static void getSuggestExerciseBaseOnBMI(int chieucao, int cannang) {
        if (chieucao == 0)
            chieucao = 1;
//        Log.i("BMICC", Integer.toString(chieucao));
//        Log.i("BMICN", Integer.toString(cannang));
//        Float BMI = 0f;
        float BMI = cannang / ((chieucao / 100.0f) * (chieucao / 100.0f));
        int level = 1;
        if (BMI < 18.5 || BMI >= 30) {
            level = 1;
        } else if (BMI >= 18.5 && BMI <= 29.9) {
            level = 2;
        };
        suggestArrayList.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getDifficulty() == level) {
                suggestArrayList.add(arrayList.get(i));
            }
        }
    }
    
    public static void setProgressBarDaily() {
        String query = "SELECT LUONGNUOCHOMNAY, GIONGUHOMNAY, TAPLUYENHOMNAY, LUONGNUOCMUCTIEU, GIONGUMUCTIEU, TAPLUYENMUCTIEU FROM USERS";
        Cursor cursor = userHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Integer nuoc = cursor.getInt(0);
                Integer ngu = cursor.getInt(1);
                Integer tapluyen = cursor.getInt(2);
                Integer nuocMT = cursor.getInt(3);
                Integer nguMT = cursor.getInt(4);
                Integer tapluyenMT = cursor.getInt(5);

                sleepPB.setMax(nguMT);
                sleepPB.setProgress(ngu);
                drinkPB.setMax(nuocMT);
                drinkPB.setProgress(nuoc);
                exercisePB.setMax(tapluyenMT);
                exercisePB.setProgress(tapluyen);
                allPB.setMax(nguMT + nuocMT + tapluyenMT);
                allPB.setProgress(ngu + nuoc + tapluyen);

                sleepPercentTV.setText(Integer.toString(Math.round((float)ngu / nguMT * 100)) + "%");
                drinkPercentTV.setText(Integer.toString(Math.round((float)nuoc / nuocMT * 100)) + "%");
                exercisePercentTV.setText(Integer.toString(Math.round((float)tapluyen / tapluyenMT * 100)) + "%");
                allGoalPercentTV.setText(Integer.toString(Math.round((float)(ngu + nuoc + tapluyen) / (nguMT + nuocMT + tapluyenMT) * 100)) + "%");
            }
        }
    }

    public static void resetName() {
        String query = "SELECT TEN FROM USERS";
        Cursor cursor = userHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                nameTV.setText("Xin chào, " + cursor.getString(0));
            }
        }
    }

    public static void setSchedules() {
        String query = "SELECT * FROM SCHEDULE ORDER BY NAM ASC, THANG ASC, NGAY ASC, TIENG ASC, TONGPHUT ASC";
        Cursor cursor = userHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String tieude = cursor.getString(0);
                String ghichu = cursor.getString(1);
                String diadiem = cursor.getString(2);
                Integer ngay = cursor.getInt(3);
                Integer thang = cursor.getInt(4);
                Integer nam = cursor.getInt(5);
                Integer tieng = cursor.getInt(6);
                Integer tongphut = cursor.getInt(7);
                String time;

                schedules.add(new ScheduleEvent(tieude, ghichu, diadiem, ngay, thang, nam, tieng, tongphut));
            }
        }
    }

    @Override
    public void onClick(int position) {
        Log.i("POSITION", Integer.toString(position));
        Intent intent1 = new Intent(getActivity(), DetailExerciseActivity.class);
        Log.i("POSITION", Integer.toString(position));
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
