package com.example.dailyhealth;

import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyhealth.database.UserHelper;
import com.example.dailyhealth.database.WeekInfoHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.Locale;

import com.github.mikephil.charting.components.AxisBase;


public class ExerciseStatistics extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_statistics);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
//        toolbarTitle.setText("Thống kê giấc ngủ");

// Khai báo BarChart và Animation
        barChart = findViewById(R.id.barChart);
        setupBarChart();
        populateBarChart();
        barChart.animateY(800);


        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //@Override
    //public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //if (item.getItemId() == android.R.id.home) {
    //onBackPressed();
    //return true;
    //}
    //return super.onOptionsItemSelected(item);
    //}
    private void setupBarChart() {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        barChart.setBorderColor(Color.BLACK); // Đặt màu đường viền
        barChart.setBorderWidth(2f); // Đặt độ rộng đường viền


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] daysOfWeek = new String[]{"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};

            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < daysOfWeek.length) {
                    return daysOfWeek[index];
                }
                return "";
            }
        });
        xAxis.setTextSize(12);


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextSize(12);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new TimeAxisValueFormatter());


        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        xAxis.setDrawGridLines(true); // Hiển thị gạch kẻ trục x
        leftAxis.setDrawGridLines(true); // Hiển thị gạch kẻ trục y
        xAxis.setGridColor(Color.GRAY); // Đặt màu gạch kẻ trục x
        leftAxis.setGridColor(Color.GRAY); // Đặt màu gạch kẻ trục y
        xAxis.enableGridDashedLine(10f, 10f, 0f); // Đặt kiểu gạch kẻ trục x (nét đứt)
        leftAxis.enableGridDashedLine(10f, 10f, 0f); // Đặt kiểu gạch kẻ trục y (nét đứt)
        xAxis.setTextColor(Color.BLACK); // Đặt màu chữ trục x
        xAxis.setTextSize(12f); // Đặt kích thước chữ trục x
        leftAxis.setTextColor(Color.BLACK); // Đặt màu chữ trục y
        leftAxis.setTextSize(12f); // Đặt kích thước chữ trục y
        barChart.getDescription().setText("Biểu đồ thời gian ngủ"); // Đặt tiêu đề
        barChart.getDescription().setTextColor(Color.BLACK); // Đặt màu chữ tiêu đề
        barChart.getDescription().setTextSize(14f); // Đặt kích thước chữ tiêu đề

    }

    public float getTime2Value(int h, int m){
        float valueY = h + (m / 60f);
        return valueY;
    }

    private void populateBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        boolean isNew = true;
        WeekInfoHelper weekInfoHelper = new WeekInfoHelper(this);
        //String query = "SELECT * FROM weekInfo WHERE SHOWABLE = 1";
        String query = "SELECT * FROM weekInfo ";
        Cursor cursor = weekInfoHelper.GetData(query);
        if (cursor.getCount() > 0) {
            int i = 0;
            while (cursor.moveToNext()) {
                String Thu = cursor.getString(1);
                int num = cursor.getInt(4);
                int showable = cursor.getInt(5);
                if (showable == 1){
                    isNew = false;
                    entries.add(new BarEntry(i, SleepStatistics.convertToHourMinuteFormat(num)));
                }
                else {
                    entries.add(new BarEntry(i, 0));
                }
                i ++;
            }
        }
        cursor.close();

        BarDataSet dataSet = new BarDataSet(entries, "Thời gian tập");
        dataSet.setColor(Color.parseColor("#B26E52"));
        dataSet.setValueTextSize(12f);
        float barWidth = 0.6f; // Độ rộng của mỗi cột (tỷ lệ phần trăm so với mặc định)


        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int hour = (int) value; // Giá trị giờ
                int minute = (int) ((value - hour) * 60); // Giá trị phút
                return hour + "h" + String.format(Locale.getDefault(), "%02d", minute);
            }
        });

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(barWidth);
        barChart.setData(barData);
        barChart.invalidate();

        float averageExerciseTime = 0f;
        float minExerciseTime =0f;
        float maxExerciseTime = 0f;

        query = "SELECT AVG(GIONGU) AS AVG_GIONGU, MAX(GIONGU) AS MAX_GIONGU, MIN(GIONGU) AS MIN_GIONGU FROM weekInfo WHERE SHOWABLE=1";
        cursor = weekInfoHelper.GetData(query);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                averageExerciseTime = cursor.getInt(0);
                minExerciseTime = cursor.getInt(2);
                maxExerciseTime = cursor.getInt(1);
            }
        }

        float muctieu = 0;
        UserHelper userHelper = new UserHelper(this);
        query = "SELECT * FROM users ";
        cursor = userHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int num = cursor.getInt(11);
                muctieu = SleepStatistics.convertToHourMinuteFormat(num);
                Log.i("","Muc tieu: " + Float.toString(muctieu));
            }
        }

        // Cập nhật TextView dựa trên giá trị trung bình
        TextView textView = findViewById(R.id.warning);
        if (isNew == false)
        {
            if (averageExerciseTime < muctieu) {
                textView.setText("Cảnh báo: Luyện tập quá ít");
                textView.setTextSize(28); // Đặt kích thước chữ là 18
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(Color.RED);
            } else if (averageExerciseTime >= muctieu && averageExerciseTime <= muctieu + SleepStatistics.convertToHourMinuteFormat(30)) {
                textView.setText("Bạn làm tốt lắm!");
                textView.setTextSize(28); // Đặt kích thước chữ là 18
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(Color.parseColor("#3EA862"));
            } else {
                textView.setText("Cảnh báo: Luyện tập quá nhiều");
                textView.setTextSize(28); // Đặt kích thước chữ là 18
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(Color.YELLOW);
            }
        }
        else
        {
            textView.setText("Chưa có thông tin");
            textView.setTextSize(28); // Đặt kích thước chữ là 18
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextColor(Color.parseColor("#3EA862"));
        }
        // Cập nhật TextView thể hiện thời gian ngủ trung bình
        TextView averageTimeTextView = findViewById(R.id.textAverage);
        averageTimeTextView.setText(SleepStatistics.convertToHourAndMinuteFormat(averageExerciseTime));

        //Cập nhật TextView thể hiện thời gian ngủ nhiều nhất
        TextView maxTimeTextView = findViewById(R.id.textMax);
        maxTimeTextView.setText(SleepStatistics.convertToHourAndMinuteFormat(maxExerciseTime));


        //Cập nhật TextView thể hiện thời gian ngủ ít nhất
        TextView minTimeTextView = findViewById(R.id.textMin);
        minTimeTextView.setText(SleepStatistics.convertToHourAndMinuteFormat(minExerciseTime));


        // Cập nhật ImageView dựa trên giá trị trung bình
        ImageView imageView = findViewById(R.id.sleepImage);
        if (isNew == true)
            imageView.setImageResource(R.drawable.image_low_sleep);
        else
        if (averageExerciseTime < muctieu) {
            imageView.setImageResource(R.drawable.image_low_sleep);
        } else if (averageExerciseTime >= muctieu && averageExerciseTime <= muctieu + SleepStatistics.convertToHourMinuteFormat(30)) {
            imageView.setImageResource(R.drawable.image_normal_sleep);
        } else {
            imageView.setImageResource(R.drawable.image_high_sleep);
        }
    }
}

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link statistics#newInstance} factory method to
 * create an instance of this fragment.
 */
