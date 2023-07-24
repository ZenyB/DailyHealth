package com.example.dailyhealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyhealth.database.UserHelper;

public class UserSetting extends AppCompatActivity {

    private UserHelper userHelper = new UserHelper(this);
    protected static EditText name, birth, height, weight;
    protected static RadioButton male, female;
    protected int gender;
    protected TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user);
        name = findViewById(R.id.userNameText);
        birth = findViewById(R.id.userBirthText);
        height = findViewById(R.id.userHeightText);
        weight = findViewById(R.id.userWeightText);
        male = findViewById(R.id.userGenderMale);
        female = findViewById(R.id.userGenderFemale);
        save = findViewById(R.id.saveButton);
        ImageButton backButton = findViewById(R.id.btnBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserSetting.this);
                builder.setMessage("Bạn có chắc muốn thay đổi thông tin này?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                name.setText("");
                                birth.setText("");
                                height.setText("");
                                weight.setText("");
                                loadData();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateData();
                                loadData();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        loadData();
    }

    private void updateData(){
        String query;
        if (!name.getText().toString().isEmpty()){
            query = "UPDATE USERS SET TEN = '" + name.getText() + "' WHERE TEN = '" + name.getHint() + "'";
            userHelper.QueryData(query);
            name.setText("");
        }
        if (!birth.getText().toString().isEmpty()){
            query = "UPDATE USERS SET NAMSINH = '" + birth.getText() + "' WHERE TEN = '" + name.getHint() + "'";
            userHelper.QueryData(query);
            birth.setText("");
        }
        if (!height.getText().toString().isEmpty()){
            query = "UPDATE USERS SET CHIEUCAO = " + height.getText() + " WHERE TEN = '" + name.getHint() + "'";
            userHelper.QueryData(query);
            height.setText("");
        }
        if (!weight.getText().toString().isEmpty()){
            query = "UPDATE USERS SET CANNANG = " + weight.getText() + " WHERE TEN = '" + name.getHint() + "'";
            userHelper.QueryData(query);
            weight.setText("");
        }

        if (male.isChecked()){
            query = "UPDATE USERS SET GIOITINH = '" + 0 + "' WHERE TEN = '" + name.getHint() + "'";
            userHelper.QueryData(query);
        } else {
            query = "UPDATE USERS SET GIOITINH = '" + 1 + "' WHERE TEN = '" + name.getHint() + "'";
            userHelper.QueryData(query);
        }
    }

    private void loadData(){
        String query ="SELECT TEN, NAMSINH, GIOITINH, CHIEUCAO, CANNANG FROM USERS";
        Cursor cursor = userHelper.GetData(query);

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                name.setHint(cursor.getString(0));
                birth.setHint(cursor.getString(1));
                gender = cursor.getInt(2);
                height.setHint(cursor.getString(3));
                weight.setHint(cursor.getString(4));
            }
        }

        if (gender == 0){
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
    }
}
