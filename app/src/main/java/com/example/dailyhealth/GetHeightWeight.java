package com.example.dailyhealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyhealth.database.UserHelper;

public class GetHeightWeight extends AppCompatActivity {

    private UserHelper userHelper = new UserHelper(this);

    protected class User {
        private String name, birth;
        private int gender, height, weight;
        private int waterTarget, sleepTarget, exerciseTarget;

        private float bmi;

        public User(){
        }

        public User(String name, String birth, int gender, int height, int weight){
            this.name = name;
            this.birth = birth;
            this.gender = gender;
            this.height = height;
            this.weight = weight;

            this.bmi = (float) weight / (((float)height/100) * ((float)height / 100));
        }

        public String getName() {
            return name;
        }
        public String getBirth() {
            return birth;
        }
        public int getGender() {
            return gender;
        }
        public int getHeight() {
            return height;
        }
        public int getWeight() {
            return weight;
        }

        public void setName(String name) {
            this.name = name;
        }
        public void setBirth(String birth) {
            this.birth = birth;
        }
        public void setGender(int gender) {
            this.gender = gender;
        }
        public void setHeight(int height) {
            this.height = height;
        }
        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

    protected static User user;

    private int luongNuoc(int weight, float bmi){
        int nuoc = weight * 35;
        Log.i("aaaaa", Integer.toString(nuoc));

        if (bmi < 18.5 || bmi > 25)
            nuoc = nuoc * 115 / 100;
        return nuoc;
    }

    protected static EditText heightUser, weightUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started3);
        Button myButton = findViewById(R.id.doneClick);
        ImageView backButton = findViewById(R.id.backButton);
        heightUser = findViewById(R.id.height_text);
        weightUser = findViewById(R.id.weight_text);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name, birth;
                int height, weight, gender;

                if (heightUser.getText().toString().isEmpty()){
                    String errorMessage = "Vui lòng nhập chiều cao!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(GetHeightWeight.this);
                    builder.setTitle("Thông báo!!!")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                } else if (weightUser.getText().toString().isEmpty()) {
                    String errorMessage = "Vui lòng nhập cân nặng!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(GetHeightWeight.this);
                    builder.setTitle("Thông báo!!!")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                } else {
                    name = GetInfor.nameUser.getText().toString();
                    birth = GetInfor.birthUser.getText().toString();
                    gender = GetInfor.gender;
                    height = Integer.parseInt(heightUser.getText().toString());
                    weight = Integer.parseInt(weightUser.getText().toString());

                    user = new User(name, birth, gender, height, weight);

                    addUser();

                    Intent intent = new Intent(GetHeightWeight.this, NavigationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void addUser(){
        String query = "INSERT INTO users (ID, TEN, NAMSINH, GIOITINH, CHIEUCAO, CANNANG, LUONGNUOCHOMNAY, GIONGUHOMNAY, TAPLUYENHOMNAY, LUONGNUOCMUCTIEU, GIONGUMUCTIEU, TAPLUYENMUCTIEU)" +
                " VALUES ('00001', '" + user.name + "', '" + user.birth + "', '" + user.gender + "', " + user.height + ", " + user.weight + ", 0, 0, 0, " + Integer.toString(luongNuoc(user.weight, user.bmi)) + ", 8, 0)";
        userHelper.QueryData(query);
        Log.i(Float.toString(user.bmi), Integer.toString(luongNuoc(user.weight, user.bmi)));
    }
}
