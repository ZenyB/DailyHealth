package com.example.dailyhealth;

import android.graphics.Typeface;
import android.os.Bundle;

import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

class TimeAxisValueFormatter extends ValueFormatter {
    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int hour = (int) value; // Giá trị giờ
        int minute = (int) ((value - hour) * 60); // Giá trị phút
        return hour + "h" + String.format(Locale.getDefault(), "%02d", minute);
    }
}


public class SleepStatistics extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_statistics);

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
        entries.add(new BarEntry(0, 8.4f));   // Thời gian ngủ thứ 2: 8h24
        entries.add(new BarEntry(1, 7.5f));   // Thời gian ngủ thứ 3: 7h30
        entries.add(new BarEntry(2, 9.9f));   // Thời gian ngủ thứ 4: 6h54
        entries.add(new BarEntry(3, 9.8f));   // Thời gian ngủ thứ 5: 7h48
        entries.add(new BarEntry(4, 9.1f));   // Thời gian ngủ thứ 6: 6h06
        entries.add(new BarEntry(5, 9.3f));   // Thời gian ngủ thứ 7: 8h18
        entries.add(new BarEntry(6, 7.2f));   // Thời gian ngủ chủ nhật: 7h12


        BarDataSet dataSet = new BarDataSet(entries, "Thời gian ngủ");
        dataSet.setColor(Color.parseColor("#3EA862"));
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

        // Tính giá trị trung bình
        float totalSleepTime = 0f;
        float minSleepTime = entries.stream().findFirst().get().getY();
        float maxSleepTime = entries.stream().findFirst().get().getY();

        for (BarEntry entry : entries) {
            if (entry.getY() < minSleepTime)
                minSleepTime = entry.getY();
            if (entry.getY() > maxSleepTime)
                maxSleepTime = entry.getY();
        }

        for (BarEntry entry : entries) {
            totalSleepTime += entry.getY();
        }
        float averageSleepTime = totalSleepTime / entries.size();

        // Cập nhật TextView dựa trên giá trị trung bình
        TextView textView = findViewById(R.id.warning);
        if (averageSleepTime < 7) {
            textView.setText("Cảnh báo: Thiếu ngủ");
            textView.setTextSize(28); // Đặt kích thước chữ là 18
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextColor(Color.RED);
        } else if (averageSleepTime >= 7 && averageSleepTime <= 8) {
            textView.setText("Bạn làm tốt lắm!");
            textView.setTextSize(28); // Đặt kích thước chữ là 18
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextColor(Color.parseColor("#3EA862"));
        } else {
            textView.setText("Cảnh báo: Ngủ quá nhiều");
            textView.setTextSize(28); // Đặt kích thước chữ là 18
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextColor(Color.YELLOW);
        }
        // Cập nhật TextView thể hiện thời gian ngủ trung bình
        TextView averageTimeTextView = findViewById(R.id.textAverage);
        int hour = (int) averageSleepTime; // Giá trị giờ
        int minute = (int) ((averageSleepTime - hour) * 60); // Giá trị phút
        averageTimeTextView.setText(String.format(hour + "h" + String.format(Locale.getDefault(), "%02d", minute)));

        //Cập nhật TextView thể hiện thời gian ngủ nhiều nhất
        TextView maxTimeTextView = findViewById(R.id.textMax);
        hour = (int) maxSleepTime; // Giá trị giờ
        minute = (int) ((maxSleepTime - hour) * 60); // Giá trị phút
        maxTimeTextView.setText(hour + "h" + String.format(Locale.getDefault(), "%02d", minute));


        //Cập nhật TextView thể hiện thời gian ngủ ít nhất
        TextView minTimeTextView = findViewById(R.id.textMin);
        hour = (int) minSleepTime; // Giá trị giờ
        minute = (int) ((minSleepTime - hour) * 60); // Giá trị phút
        minTimeTextView.setText(String.format(hour + "h" + String.format(Locale.getDefault(), "%02d", minute)));


        // Cập nhật ImageView dựa trên giá trị trung bình
        ImageView imageView = findViewById(R.id.sleepImage);
        if (averageSleepTime < 7) {
            imageView.setImageResource(R.drawable.image_low_sleep);
        } else if (averageSleepTime >= 7 && averageSleepTime <= 8) {
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
