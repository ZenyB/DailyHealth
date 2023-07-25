package com.example.dailyhealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dailyhealth.database.UserHelper;

import java.util.Currency;

public class WaterDaily extends AppCompatActivity {

    private UserHelper userHelper = new UserHelper(this);
    private ProgressBar progressBar;
    private int amountDrink = 200;
    private int progressValue = 0;
    private int progressMax = 0;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_daily);

        progressBar = findViewById(R.id.progressBar);
        Button drinkWater = findViewById(R.id.buttonDrink);
        TextView water = findViewById(R.id.water);
        TextView amount = findViewById(R.id.amountWaterCup);
        Button cup_1 = findViewById(R.id.buttonCup1);
        Button cup_2 = findViewById(R.id.buttonCup2);
        Button cup_3 = findViewById(R.id.buttonCup3);
        Button cup_4 = findViewById(R.id.buttonCup4);
        Button cup_5 = findViewById(R.id.buttonCup5);
        EditText inputAmountWater = findViewById(R.id.inputAmount);
        Button amountWater = findViewById(R.id.buttonAmoutWater);

        ImageView backButton = findViewById(R.id.backButton);

        InitWaterProgress();

        water.setText(progressValue + "/" + progressBar.getMax() +"ml");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        amountWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputAmountWater.getText().toString();

                if (text.isEmpty()){
                    String errorMessage = "Vui lòng nhập lượng nước đã uống!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(WaterDaily.this);
                    builder.setTitle("Warning!!!")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                int temp = Integer.parseInt(inputAmountWater.getText().toString());

                if (temp > 0 && temp < 1000) {
                    amount.setText(inputAmountWater.getText().toString() + " ml");
                    amountDrink = Integer.parseInt(inputAmountWater.getText().toString());
                }
                else {
                    String errorMessage = "Lượng nước nhập vào không phù hợp!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(WaterDaily.this);
                    builder.setTitle("Warning!!!")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });

        drinkWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setProgress(progressBar.getProgress() + amountDrink,true);
                amount.setText(cup_1.getText());

                progressValue = progressBar.getProgress();
                water.setText(progressValue + "/" + progressBar.getMax() +"ml");

                String query = "UPDATE users SET LUONGNUOCHOMNAY = " + Integer.toString(progressValue) + " WHERE TEN = '" + name + "'";
                userHelper.QueryData(query);
            }
        });

        cup_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText(cup_1.getText());
                amountDrink = 100;
            }
        });

        cup_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText(cup_2.getText());
                amountDrink = 150;
            }
        });

        cup_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText(cup_3.getText());
                amountDrink = 200;
            }
        });

        cup_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText(cup_4.getText());
                amountDrink = 300;
            }
        });

        cup_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText(cup_5.getText());
                amountDrink = 500;
            }
        });
    }

    private void InitWaterProgress() {
        String query = "SELECT LUONGNUOCHOMNAY, LUONGNUOCMUCTIEU, TEN FROM USERS";
        Cursor cursor = userHelper.GetData(query);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                progressBar.setProgress(cursor.getInt(0), true);
                Log.i(cursor.getString(2), Integer.toString(cursor.getInt(1)));
                progressBar.setMax(cursor.getInt(1));
                name = cursor.getString(2);
                progressValue = cursor.getInt(0);
            }
        }
    }
}