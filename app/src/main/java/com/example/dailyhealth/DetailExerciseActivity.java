package com.example.dailyhealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailExerciseActivity extends AppCompatActivity {

    private ArrayList<SmallExercise> arrayList = new ArrayList<>();
    public static MainExercise mainExercise;
    ImageButton imgButton;
    public static ImageButton startBtn;
    public static TextView relaxTV, statusTV;
    TextView mainNameTV, mainTimeTV;

    private int positionNow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_exercise);

        int position = getIntent().getIntExtra("position", 0);
        mainNameTV = findViewById(R.id.exerciseNameTextView);
        mainTimeTV = findViewById(R.id.timeTextView);
        startBtn = findViewById(R.id.btnPlayPause);
        relaxTV = findViewById(R.id.relaxTimeTV);
        statusTV = findViewById(R.id.statusTextView);

        if (getIntent().getStringExtra("type").equals("all")) {
            mainExercise = HomeFragment.arrayList.get(position);
            mainNameTV.setText(HomeFragment.arrayList.get(position).getName());
            mainTimeTV.setText(Integer.toString(HomeFragment.arrayList.get(position).getDuration()) + " phút");
            arrayList = HomeFragment.arrayList.get(position).getSmallExercises();
        } else {
            mainExercise = HomeFragment.suggestArrayList.get(position);
            arrayList = HomeFragment.suggestArrayList.get(position).getSmallExercises();
            mainNameTV.setText(HomeFragment.suggestArrayList.get(position).getName());
            mainTimeTV.setText(Integer.toString(HomeFragment.suggestArrayList.get(position).getDuration()) + " phút");
        }

        final RecyclerView r = (RecyclerView) findViewById(R.id.detailExerciseRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new SmallExerciseAdapter(this, arrayList));

        imgButton=findViewById(R.id.btnBack);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitConfirmationDialog();
            }
        });
    }

    private void showExitConfirmationDialog() {
        // Xây dựng hộp thoại thông báo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn có chắc chắn muốn thoát?");
        builder.setMessage("Nếu bạn không hoàn thành tất cả bài tập thì sẽ không được tính giờ luyện tập!");

        // Thiết lập nút "Đồng ý" cho hộp thoại
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thoát ứng dụng hoặc thực hiện các thao tác khi người dùng đồng ý thoát
                DetailExerciseActivity.super.finish(); // Kết thúc hoạt động hiện tại
            }
        });

        // Thiết lập nút "Hủy" cho hộp thoại
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Đóng hộp thoại khi người dùng chọn "Hủy"
                dialog.dismiss();
            }
        });

        // Hiển thị hộp thoại
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }
}