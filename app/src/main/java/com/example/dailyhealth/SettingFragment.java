package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SettingFragment extends Fragment implements SettingAdapter.OnSettingItemClick {

    private ArrayList<Integer> arrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);

        final RecyclerView r = (RecyclerView) view.findViewById(R.id.settingRecyclerView);
        r.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new SettingAdapter(getActivity(), arrayList, this));
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSettingClick(int position) {
        Log.i("Setting", "abcd");
        Intent intent;
        int a = arrayList.get(position);
        Log.i("Setting", Integer.toString(a));
        switch (a) {
            case 0:
                intent = new Intent(getActivity(), ScheduleCalendar.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(getActivity(), SleepManagement.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getActivity(), WaterDaily.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(getActivity(), MoonCalendar.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(getActivity(), UserSetting.class);
                startActivity(intent);
                break;
        }
    }
}
