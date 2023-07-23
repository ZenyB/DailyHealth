package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class GetInfor extends AppCompatActivity {
    protected static EditText nameUser, birthUser;
    protected static int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started2);
        Button myButton = findViewById(R.id.nextButton1);
        RadioGroup genderRadio = findViewById(R.id.gender_radio_group);
        birthUser = findViewById(R.id.birthEditText);
        nameUser = findViewById(R.id.nameEditText);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textname = nameUser.getText().toString();
                String textBirth = birthUser.getText().toString();
                int selectedGender = genderRadio.getCheckedRadioButtonId();

                if (textname.isEmpty()){
                    String errorMessage = "Vui lòng nhập tên người dùng!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(GetInfor.this);
                    builder.setTitle("Thông báo!!!")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                } else if (textBirth.isEmpty()) {
                    String errorMessage = "Vui lòng nhập năm sinh!";
                    AlertDialog.Builder builder = new AlertDialog.Builder(GetInfor.this);
                    builder.setTitle("Thông báo!!!")
                            .setMessage(errorMessage)
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                } else {
                    if (selectedGender == R.id.male_radio_button){
                        gender = 0;
                    } else if (selectedGender == R.id.female_radio_button) {
                        gender = 1;
                    } else {
                        gender = -1;
                    }

                    Intent intent = new Intent(GetInfor.this, GetHeightWeight.class);
                    startActivity(intent);
                }
            }
        });
    }
}
