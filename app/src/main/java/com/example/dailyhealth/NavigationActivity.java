package com.example.dailyhealth;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class NavigationActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.property_1_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.property_1_exercise));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.property_1_setting));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.property_1_user));
        bottomNavigation.show(1, true);
        bottomNavigation.setOnClickMenuListener(model -> {
            // YOUR CODES
            switch (model.getId()){
                case 1:
                    loadFragment(new HomeFragment());
                    break;
                case 2:
                    loadFragment(new ExerciseFragment());
                    break;
                case 3:
                    loadFragment(new SettingFragment());
                    break;
                case 4:
                    loadFragment(new UserFragment());
                    break;
            }
            return null;
        });

        bottomNavigation.setOnShowListener(model -> {
            // YOUR CODES
            switch (model.getId()){
                case 1:
                    loadFragment(new HomeFragment());
                    break;
                case 2:
                    loadFragment(new ExerciseFragment());
                    break;
                case 3:
                    loadFragment(new SettingFragment());
                    break;
                case 4:
                    loadFragment(new UserFragment());
                    break;
            }
            return null;
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment, null)
                .commit();
    }

}
