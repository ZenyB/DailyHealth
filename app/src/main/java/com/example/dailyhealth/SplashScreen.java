package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dailyhealth.database.UserHelper;
import com.example.dailyhealth.database.WeekInfoHelper;

import java.util.concurrent.TimeUnit;

import java.util.ArrayList;

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
        // Tạo và lưu trữ mốc thời gian khi ngày mới bắt đầu
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long lastNewDayTime = sharedPref.getLong(KEY_LAST_NEW_DAY_TIME, 0);
        long currentTime = System.currentTimeMillis();

        if (isNewDay(lastNewDayTime, currentTime)) {
            // Nếu đã qua ngày mới, thực hiện công việc kiểm tra ngày mới ở đây
            // Ví dụ: lưu trữ dữ liệu mới cho ngày mới, cập nhật thông tin ứng dụng, ...
            // Sau đó cập nhật lại mốc thời gian ngày mới bắt đầu
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong(KEY_LAST_NEW_DAY_TIME, currentTime);
            editor.apply();

            // Lên lịch thực hiện công việc kiểm tra ngày mới với OneTimeWorkRequest
            OneTimeWorkRequest newDayCheckWork = new OneTimeWorkRequest.Builder(CheckNewDayWorker.class)
                    .setInitialDelay(getTimeUntilNextDayStart(), TimeUnit.MILLISECONDS)
                    .addTag(WORK_TAG)
                    .build();
            WorkManager.getInstance(this).enqueue(newDayCheckWork);
        } else {
            // Ngày mới chưa đến, hủy bỏ công việc cũ nếu có
            WorkManager.getInstance(this).cancelAllWorkByTag(WORK_TAG);
        }
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

            //Tạo bảng weekInfo
            query = "CREATE TABLE IF NOT EXISTS weekInfo" +
                    "(ID TEXT PRIMARY KEY, THU TEXT, " +
                    "LUONGNUOC INTEGER, GIONGU INTEGER, TAPLUYEN INTEGER)";

            WeekInfoHelper weekInfoHelper = new WeekInfoHelper(this);
            weekInfoHelper.QueryData(query);

//            // Start start activity
//            Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SplashScreen.this, GetStart.class));
//            return;
        }
        // Start home activity
//        Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(SplashScreen.this, NavigationActivity.class));
//        return;

        //Kiểm tra users có dữ liệu nào chưa
        String query = "SELECT * FROM users";

        UserHelper userHelper = new UserHelper(this);
        Cursor cursor = userHelper.GetData(query);

        if (cursor.getCount() > 0) {
            //Get các loại dữ liệu cần dùng chung

            //User đã cài đặt thông tin ban đầu => Home
            // Start home activity
            Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashScreen.this, NavigationActivity.class));
            return;
        }
        else {
            //User chưa cài đặt thông tin ban đầu => Start
            // Start start activity
            Toast.makeText(this, "Test SplashScreen", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashScreen.this, GetStart.class));
            return;
        }
    }

    boolean isTableExist(SQLiteDatabase db, String table) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{table});
        boolean tableExist = (cursor.getCount() != 0);
        cursor.close();
        return tableExist;
    }
    private long getMillisUntilNextMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTimeInMillis() - System.currentTimeMillis();
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
}