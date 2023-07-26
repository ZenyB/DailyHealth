package com.example.dailyhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dailyhealth.database.MoonHelper;
import com.example.dailyhealth.database.ScheduleHelper;
import com.example.dailyhealth.database.UserHelper;
import com.example.dailyhealth.database.WeekInfoHelper;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    public String databaseName = "DAILYHEATH";

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
//        MoonHelper moonHelper = new MoonHelper(this);
//        String query1 = "DROP TABLE MOON";
//        moonHelper.QueryData(query1);
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

    public static Boolean isTableExist(SQLiteDatabase db, String table) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{table});
        boolean tableExist = (cursor.getCount() != 0);
        cursor.close();
        return tableExist;
    }
}