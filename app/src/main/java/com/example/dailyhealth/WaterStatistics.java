package com.example.dailyhealth;

import android.database.Cursor;
import android.graphics.Typeface;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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


public class WaterStatistics extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_statistics);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Thống kê lượng nước");

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
    private float convertToMilliliters(float valueInLiters) {
        return valueInLiters * 1000; // Chuyển đổi từ L sang ml
    }
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
        List<BarEntry> entriesInMilliliters = new ArrayList<>();
        boolean isNew = true;

        WeekInfoHelper weekInfoHelper = new WeekInfoHelper(this);
        //String query = "SELECT * FROM weekInfo WHERE SHOWABLE = 1";
        String query = "SELECT * FROM weekInfo ";
        Cursor cursor = weekInfoHelper.GetData(query);
        if (cursor.getCount() > 0) {
            int i = 0;
            while (cursor.moveToNext()) {
                String Thu = cursor.getString(1);
                int num = cursor.getInt(2);
                int showable = cursor.getInt(5);
                if (showable == 1){
                    isNew = false;
                    entriesInMilliliters.add(new BarEntry(i, num));
                }
                else {
                    entriesInMilliliters.add(new BarEntry(i, 0));
                }
                i ++;
            }
        }
        // Tạo một danh sách mới để lưu trữ giá trị Y của các cột đã chuyển đổi sang ml

        BarDataSet dataSet = new BarDataSet(entriesInMilliliters, "Nước uống");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextSize(12f);
        float barWidth = 0.6f; // Độ rộng của mỗi cột (tỷ lệ phần trăm so với mặc định)


        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value + "ml";
            }
        });

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(barWidth);
        barChart.setData(barData);
        barChart.invalidate();

        float averageWater = 0f;
        float minWater =0f;
        float maxWater = 0f;

        query = "SELECT AVG(LUONGNUOC) AS AVG_GIONGU, MAX(LUONGNUOC) AS MAX_GIONGU, MIN(LUONGNUOC) AS MIN_GIONGU FROM weekInfo WHERE SHOWABLE=1";
        cursor = weekInfoHelper.GetData(query);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                averageWater = cursor.getInt(0);
                minWater = cursor.getInt(2);
                maxWater = cursor.getInt(1);
            }
        }
        int muctieu = 0;
        UserHelper userHelper = new UserHelper(this);
        query = "SELECT * FROM users";
        cursor = userHelper.GetData(query);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                muctieu = cursor.getInt(9);
                Log.i("","Muc tieu: " + Float.toString(muctieu));
            }
        }
        // Cập nhật TextView dựa trên giá trị trung bình
        TextView textView = findViewById(R.id.warning);
        if (isNew == true){
            textView.setText("Chưa có thông tin");
            textView.setTextSize(28); // Đặt kích thước chữ là 18
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextColor(Color.parseColor("#3EA862"));

        }

        else {
            if (averageWater < muctieu) {
                textView.setText("Bạn nên uống nước thêm");
                textView.setTextSize(28); // Đặt kích thước chữ là 18
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(Color.RED);
            } else if (averageWater >= muctieu && averageWater <= muctieu + 1000) {
                textView.setText("Bạn làm tốt lắm!");
                textView.setTextSize(28); // Đặt kích thước chữ là 18
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(Color.parseColor("#3EA862"));
            } else {
                textView.setText("Cảnh báo: Uống nước quá nhiều");
                textView.setTextSize(28); // Đặt kích thước chữ là 18
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextColor(Color.YELLOW);
            }
        }
        // Cập nhật TextView thể hiện thời gian ngủ trung bình
        TextView averageTimeTextView = findViewById(R.id.textAverage);
        averageTimeTextView.setText(averageWater + "ml");

        //Cập nhật TextView thể hiện thời gian ngủ nhiều nhất
        TextView maxTimeTextView = findViewById(R.id.textMax);
        maxTimeTextView.setText(maxWater + "ml");


        //Cập nhật TextView thể hiện thời gian ngủ ít nhất
        TextView minTimeTextView = findViewById(R.id.textMin);
        minTimeTextView.setText(minWater + "ml");


        // Cập nhật ImageView dựa trên giá trị trung bình
        ImageView imageView = findViewById(R.id.sleepImage);
        if (isNew == true)
            imageView.setImageResource(R.drawable.image_low_sleep);
        else
        if (averageWater < muctieu) {
            imageView.setImageResource(R.drawable.image_low_sleep);
        } else if (averageWater >= muctieu && averageWater <= muctieu + 1000) {
            imageView.setImageResource(R.drawable.image_normal_sleep);
        } else {
            imageView.setImageResource(R.drawable.image_high_sleep);
        }
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextSize(12);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Sử dụng DecimalFormat để định dạng giá trị và loại bỏ phần thập phân nếu không có số thập phân
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formattedValue = decimalFormat.format(value);
                if (formattedValue.endsWith(".0")) {
                    formattedValue = formattedValue.substring(0, formattedValue.length() - 2);
                }
                return formattedValue + " ml"; // Hiển thị giá trị với đơn vị ml
            }
        });
    }
}

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link statistics#newInstance} factory method to
 * create an instance of this fragment.
 */
