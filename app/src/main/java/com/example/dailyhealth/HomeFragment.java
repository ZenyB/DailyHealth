package com.example.dailyhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HorizontalExerciseAdapter.OnItemClick, MainScheduleAdapter.OnItemClick {

    private ArrayList<MainExercise> arrayList = new ArrayList<>();
    private ArrayList<Schedule> schedules = new ArrayList<>();

    TextView drinkTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        arrayList.add(new MainExercise("Bài tập chính", "5 phút", "200KCAL"));
        arrayList.add(new MainExercise("Bài tập cơ tay", "5 phút", "200KCAL"));

        final RecyclerView r = (RecyclerView) view.findViewById(R.id.exMainRV);
        r.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r.setAdapter(new HorizontalExerciseAdapter(getActivity(), arrayList, this));

        schedules.add(new Schedule("Đi du lịch - ngày 12/4/2023"));
        schedules.add(new Schedule("Đi đến phòng tập - ngày 15/7/2023"));

        final RecyclerView r1 = (RecyclerView) view.findViewById(R.id.scheduleMainRV);
        r1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        Toast.makeText(getApplicationContext(), arrayList.size(), Toast.LENGTH_SHORT).show();
        r1.setAdapter(new MainScheduleAdapter(getActivity(), schedules, this));

        drinkTV = view.findViewById(R.id.amountWaterTV);
        drinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate qua màn hình uống nước
                Toast.makeText(getActivity(), "Navigate to Uống nước", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onClick(int position) {
        MainExercise i = arrayList.get(position);
        Intent intent1 = new Intent(getActivity(), DetailExerciseActivity.class);
        intent1.putExtra("Name", new String(i.getName()));
//                    intent1.putExtra("Name", new String(s.getName()));
//                    intent1.putExtra("Students", new Integer(s.getStudents()));
        startActivity(intent1);
    }

    @Override
    public void onScheduleClick(int position) {
        MainExercise i = arrayList.get(position);
        //Navigate qua màn hình schedule
//        Intent intent1 = new Intent(this, ExerciseMainActivity.class);
////        intent1.putExtra("Title", new String(i.getName()));
////                    intent1.putExtra("Name", new String(s.getName()));
////                    intent1.putExtra("Students", new Integer(s.getStudents()));
//        startActivity(intent1);
    }
}
