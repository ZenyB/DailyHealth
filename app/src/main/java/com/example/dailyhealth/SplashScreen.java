package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dailyhealth.database.MoonHelper;
import com.example.dailyhealth.database.ScheduleHelper;
import com.example.dailyhealth.database.UserHelper;
import com.example.dailyhealth.database.WeekInfoHelper;

import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {

    public String databaseName = "DAILYHEATH";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String KEY_LAST_NEW_DAY_TIME = "lastNewDayTime";
    private static final String WORK_TAG = "newDayCheckWorker";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDatabase();

        // close splash activity
        finish();
    }

    private void loadDatabase() {
        //        listProduct.clear();
        SQLiteDatabase db = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);


        if (!isTableExist(db, "users")) {
            //Khởi tạo các loại bảng trong lần đầu tiên vào app
            //Tạo bảng users
            String query = "CREATE TABLE IF NOT EXISTS users" +
                    "(ID TEXT PRIMARY KEY, TEN TEXT, NAMSINH TEXT, GIOITINH INTEGER, CHIEUCAO INTEGER, CANNANG INTEGER, " +
                    "LUONGNUOCHOMNAY INTEGER, GIONGUHOMNAY INTEGER, TAPLUYENHOMNAY INTEGER, " +
                    "LUONGNUOCMUCTIEU INTEGER, GIONGUMUCTIEU INTEGER, TAPLUYENMUCTIEU INTEGER)";

            UserHelper userHelper = new UserHelper(this);
            userHelper.QueryData(query);

            //Tạo bảng moon
//            query = "CREATE TABLE IF NOT EXISTS moon" +
//                    "(ID TEXT PRIMARY KEY, NGAYBATDAU INTEGER, THANGBATDAU INTEGER, NAMBATDAU INTEGER, TRUNGBINHCHUKY INTEGER, " +
//                    "TRUNGBINHKINHNGUYET INTEGER, THOIGIANNHACTRUOC INTEGER)";
//            MoonHelper moonHelper = new MoonHelper(this);
//            moonHelper.QueryData(query);

            //Tạo bảng schedule
            query = "CREATE TABLE IF NOT EXISTS schedule" +
                    "(ID TEXT PRIMARY KEY, TIEUDE TEXT, GHICHU TEXT, DIADIEM TEXT, " +
                    "NGAY INTEGER, THANG INTEGER, NAM INTEGER, TIENG INTEGER, TONGPHUT INTEGER)";
            ScheduleHelper scheduleHelper = new ScheduleHelper(this);
            scheduleHelper.QueryData(query);

            //Tạo bảng weekInfo
            query = "CREATE TABLE IF NOT EXISTS weekInfo" +
                    "(ID TEXT PRIMARY KEY, THU TEXT, " +
                    "LUONGNUOC INTEGER, GIONGU INTEGER, TAPLUYEN INTEGER,SHOWABLE INTEGER)";

            WeekInfoHelper weekInfoHelper = new WeekInfoHelper(this);
            weekInfoHelper.QueryData(query);

            String[] days = {"THU HAI", "THU BA", "THU TU", "THU NAM", "THU SAU", "THU BAY", "CHU NHAT"};
            for (int i = 0; i < days.length; i++) {
                String insertQuery = "INSERT INTO weekInfo (ID, THU, LUONGNUOC, GIONGU, TAPLUYEN, SHOWABLE) " +
                        "VALUES ('ID_ngay_moi_" + i + "', '" + days[i] + "', 0, 0, 0, 0)";
                weekInfoHelper.QueryData(insertQuery);
            }

            String query_temp = "SELECT * FROM weekInfo";
            Cursor cursor = weekInfoHelper.GetData(query_temp);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String thu = cursor.getString(1);
                    int luongNuoc = cursor.getInt(2);
                    int giongU = cursor.getInt(3);
                    int tapLuyen = cursor.getInt(4);
                    int showable = cursor.getInt(5);

                    Log.i("WeekInfoData", "ID: " + id + ", THU: " + thu + ", LUONGNUOC: " + luongNuoc +
                            ", GIONGU: " + giongU + ", TAPLUYEN: " + tapLuyen + ", SHOWABLE: " + showable);
                }
            }
//            // Start start activity
//            Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SplashScreen.this, GetStart.class));
//            return;
        }

        // Start home activity
//        Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(SplashScreen.this, NavigationActivity.class));
//        return;
//        MoonHelper moonHelper = new MoonHelper(this);
//        String query1 = "DROP TABLE MOON";
//        moonHelper.QueryData(query1);
        //Kiểm tra users có dữ liệu nào chưa
        String query = "SELECT * FROM users";

        UserHelper userHelper = new UserHelper(this);
        Cursor cursor = userHelper.GetData(query);
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long lastNewDayTime = sharedPref.getLong(KEY_LAST_NEW_DAY_TIME, 0);
        Log.i("","" + lastNewDayTime);
        long currentTime = System.currentTimeMillis();
        Log.i(""," " + currentTime);

        if (cursor.getCount() > 0) {
            // Tạo và lưu trữ mốc thời gian khi ngày mới bắt đầu

            if (isNewDay(lastNewDayTime, currentTime)) {
                Log.i("","True, reset data");
                // Nếu đã qua ngày mới, thực hiện công việc kiểm tra ngày mới ở đây
                // Ví dụ: lưu trữ dữ liệu mới cho ngày mới, cập nhật thông tin ứng dụng, ...
                // Sau đó cập nhật lại mốc thời gian ngày mới bắt đầu
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(KEY_LAST_NEW_DAY_TIME, currentTime);
                editor.apply();

                // Hiển thị thông báo màn hình SplashScreen
                showSplashScreenNotification();
                // Lưu thông tin vào bảng weekInfo trước khi reset
                savePreviousDayData();

                // Reset các thông số về 0 trong bảng users
                resetUserData();

                // Cập nhật lại mốc thời gian ngày mới bắt đầu sau khi hiển thị thông báo
                currentTime = System.currentTimeMillis();
                sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.putLong(KEY_LAST_NEW_DAY_TIME, currentTime);
                editor.apply();
            } else {
                // Ngày mới chưa đến, hủy bỏ công việc cũ nếu có
                WorkManager.getInstance(this).cancelAllWorkByTag(WORK_TAG);
                // Lên lịch thực hiện công việc kiểm tra ngày mới với OneTimeWorkRequest
                OneTimeWorkRequest newDayCheckWork = new OneTimeWorkRequest.Builder(CheckNewDayWorker.class)
                        .setInitialDelay(getTimeUntilNextDayStart(), TimeUnit.MILLISECONDS)
                        .addTag(WORK_TAG)
                        .build();
                WorkManager.getInstance(this).enqueue(newDayCheckWork);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(KEY_LAST_NEW_DAY_TIME, currentTime);
                editor.apply();
            }
            //Get các loại dữ liệu cần dùng chung
            WeekInfoHelper weekInfoHelper = new WeekInfoHelper(this);
            String query_temp = "SELECT * FROM weekInfo";
            Cursor cursor2 = weekInfoHelper.GetData(query_temp);

            if (cursor2.getCount() > 0) {
                while (cursor2.moveToNext()) {
                    String id = cursor2.getString(0);
                    String thu = cursor2.getString(1);
                    int luongNuoc = cursor2.getInt(2);
                    int giongU = cursor2.getInt(3);
                    int tapLuyen = cursor2.getInt(4);
                    int showable = cursor2.getInt(5);

                    Log.i("WeekInfoData", "ID: " + id + ", THU: " + thu + ", LUONGNUOC: " + luongNuoc +
                            ", GIONGU: " + giongU + ", TAPLUYEN: " + tapLuyen + ", SHOWABLE: " + showable);
                }
            }

            //User đã cài đặt thông tin ban đầu => Home
            // Start home activity
       //     Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashScreen.this, NavigationActivity.class));
            return;
        }
        else {
            //User chưa cài đặt thông tin ban đầu => Start
            // Start start activity
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong(KEY_LAST_NEW_DAY_TIME, currentTime);
            editor.apply();
          //  Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashScreen.this, GetStart.class));
            return;
        }

    }

    public static Boolean isTableExist(SQLiteDatabase db, String table) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{table});
        boolean tableExist = (cursor.getCount() != 0);
        cursor.close();
        return tableExist;
    }
    // Hàm kiểm tra xem có phải đã qua ngày mới hay không
    private boolean isNewDay(long lastNewDayTime, long currentTime) {
        Calendar lastDay = Calendar.getInstance();
        lastDay.setTimeInMillis(lastNewDayTime);

        Calendar currentDay = Calendar.getInstance();
        currentDay.setTimeInMillis(currentTime);

        return lastDay.get(Calendar.DAY_OF_YEAR) != currentDay.get(Calendar.DAY_OF_YEAR)
                || lastDay.get(Calendar.YEAR) != currentDay.get(Calendar.YEAR);
    }

    // Hàm tính thời gian đến lúc bắt đầu ngày mới tiếp theo
    private long getTimeUntilNextDayStart() {
        Calendar nextDayStart = Calendar.getInstance();
        nextDayStart.add(Calendar.DAY_OF_YEAR, 1);
        nextDayStart.set(Calendar.HOUR_OF_DAY, 0);
        nextDayStart.set(Calendar.MINUTE, 0);
        nextDayStart.set(Calendar.SECOND, 0);
        nextDayStart.set(Calendar.MILLISECOND, 0);

        long currentTime = System.currentTimeMillis();
        return nextDayStart.getTimeInMillis() - currentTime;
    }

    // hàm thực hiện các phương thức reset và lưu thông tin sau khi qua ngày mới
    public void savePreviousDayData() {
        // Lưu thông tin vào bảng weekInfo
        SQLiteDatabase db = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);

        // Lấy thông tin ngày hôm trước khi reset
        String previousDay = getDayOfWeekBeforeReset();

        // Lấy dữ liệu từ bảng users
        String query = "SELECT LUONGNUOCHOMNAY, GIONGUHOMNAY, TAPLUYENHOMNAY FROM users";
        UserHelper userHelper = new UserHelper(this);
        Cursor cursor = userHelper.GetData(query);

        // Nếu có dữ liệu trong bảng users
        if (cursor.getCount() > 0) {
            // Di chuyển con trỏ đến dòng đầu tiên
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    // Lấy dữ liệu cần lưu từ bảng users
                    int luongNuocHomNay = cursor.getInt(0);
                    int giongUHomNay = cursor.getInt(1);
                    int tapLuyenHomNay = cursor.getInt(2);


                    // Kiểm tra xem ngày hiện tại đã tồn tại trong bảng weekInfo chưa
                    String checkQuery = "SELECT * FROM weekInfo WHERE THU='" + previousDay + "'";
                    Cursor checkCursor = db.rawQuery(checkQuery, null);
                    WeekInfoHelper weekInfoHelper = new WeekInfoHelper(this);
                    if (checkCursor.getCount() > 0) {
                        // Ngày hiện tại đã tồn tại trong bảng weekInfo, thực hiện cập nhật giá trị
                        String updateQuery = "UPDATE weekInfo SET LUONGNUOC=" + luongNuocHomNay +
                                ", GIONGU=" + giongUHomNay + ", TAPLUYEN=" + tapLuyenHomNay +
                                ", SHOWABLE=1 WHERE THU='" + previousDay + "'";
                        weekInfoHelper.QueryData(updateQuery);
                    } else {
                        // Kiểm tra xem ngày hiện tại đã là thứ 2 chưa
                        Calendar calendar = Calendar.getInstance();
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                        if (dayOfWeek == Calendar.TUESDAY) {
                            // Nếu ngày hiện tại là thứ 2 (qua tuần mới), reset tất cả các hàng trừ thứ 2
                            String resetQuery = "UPDATE weekInfo SET LUONGNUOC = 0, GIONGU = 0, TAPLUYEN = 0 ,SHOWABLE = 0 WHERE THU != 'THU HAI'";
                            weekInfoHelper.QueryData(resetQuery);
                        }
                        // Ngày hiện tại chưa tồn tại trong bảng weekInfo, thêm hàng mới
                        String insertQuery = "INSERT INTO weekInfo (ID, THU, LUONGNUOC, GIONGU, TAPLUYEN, SHOWABLE) VALUES " +
                                "('ID_ngay_moi', '" + previousDay + "', " + luongNuocHomNay + ", " + giongUHomNay +
                                ", " + tapLuyenHomNay + ", 1)";
                        weekInfoHelper.QueryData(insertQuery);
                    }
                }
            }
        }

        // Đóng cursor và database
        cursor.close();
        db.close();
    }

    public void resetUserData() {
        // Reset các thông số về 0 trong bảng users
        SQLiteDatabase db = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);

        String updateQuery = "UPDATE users SET LUONGNUOCHOMNAY = 0, GIONGUHOMNAY = 0, TAPLUYENHOMNAY = 0";
        UserHelper userHelper = new UserHelper(this);
        userHelper.QueryData(updateQuery);

        // Đóng database
        db.close();
    }
    public String getDayOfWeekBeforeReset() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] days = {"CHU NHAT", "THU HAI", "THU BA", "THU TU", "THU NAM", "THU SAU", "THU BAY"};
        return days[dayOfWeek - 2]; // Trừ 2 vì ngày trong tuần bắt đầu từ thứ 2
    }

    private void showSplashScreenNotification() {
        // Tạo channel cho notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "SPLASH_SCREEN";
            CharSequence name = "Splash Screen";
            String description = "Notification channel for Splash Screen";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Hiển thị thông báo
        Intent intent = new Intent(this, SplashScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "SPLASH_SCREEN")
                .setSmallIcon(R.drawable.icon_sleep)
                .setContentTitle("Ngày mới")
                .setContentText("Ngày mới vui vẻ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = (int) System.currentTimeMillis();
        notificationManager.notify(notificationId, builder.build());
    }

}