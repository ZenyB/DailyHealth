package com.example.dailyhealth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyhealth.database.UserHelper;

import java.util.ArrayList;
import java.util.logging.Handler;

public class SmallExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Activity activity;
    ArrayList<SmallExercise> smallExercises;
    int index = -1;
    int indexReal = -1;
    private int totalTimeInSeconds = 5;
    private int intervalInSeconds = 1;
    private int totalRelaxSeconds = 15;
    private int current = 0;
    private CountDownTimer countDownTimer;
    private UserHelper userHelper;
    private Context context;
//    private Handler handler;

    public SmallExerciseAdapter(Activity activity, ArrayList<SmallExercise> smallExercises) {
        this.activity = activity;
        this.smallExercises = smallExercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getRootView().getContext();
        userHelper = new UserHelper(context);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        DetailExerciseActivity.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExerciseActivity.startBtn.setBackgroundResource(R.drawable.ic_play_disable);
                DetailExerciseActivity.statusTV.setTextColor(context.getResources().getColor(R.color.gray));
                index = 0;
                indexReal = index;
                notifyDataSetChanged();
                DetailExerciseActivity.startBtn.setEnabled(false);
            }
        });

        switch (viewType) {
            case 0:
                View view1 = inflater.inflate(R.layout.small_exercise_item, parent, false);
                viewHolder = new ViewHolder1(view1);
                break;
            case 1:
                View view2 = inflater.inflate(R.layout.small_exercise_item_activated, parent, false);
                viewHolder = new ViewHolderActivated(view2);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SmallExercise smallExercise = smallExercises.get(position);
        if (DetailExerciseActivity.mainExercise.getName().equals("Bài tập bụng để test")) {
            totalRelaxSeconds = 3;
        }
        Log.i("binding", Integer.toString(position));
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder1 type1ViewHolder = (ViewHolder1) holder;
                type1ViewHolder.linear.setBackgroundResource(R.drawable.border);
                type1ViewHolder.nameTextView.setText(smallExercise.getExerciseName());

                if (holder.getAdapterPosition() < indexReal) {
                    Log.i("Exercise", type1ViewHolder.nameTextView.getText().toString());
                    Log.i("Exercise position", Integer.toString(holder.getAdapterPosition()));
                    Log.i("Exercise index", Integer.toString(indexReal));
                    type1ViewHolder.linear.setBackgroundResource(R.drawable.border_gray);
                }
                if (smallExercise.getExerciseType() == 0)
                    type1ViewHolder.timeTextView.setText(Integer.toString(smallExercise.getExerciseDuration()) + " giây");
                else
                    type1ViewHolder.timeTextView.setText(Integer.toString(smallExercise.getExerciseRepetitions()) + " lần");
                break;
            case 1:
                ViewHolderActivated type2ViewHolder = (ViewHolderActivated) holder;
                type2ViewHolder.nameTextView.setText(smallExercise.getExerciseName());
                type2ViewHolder.doneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countDownTimer = null;
                        int temp = index;
                        index = -1;
                        notifyItemChanged(indexReal);
                        indexReal = indexReal + 1;
                        if (position == smallExercises.size() - 1) {
                            String query = "SELECT TAPLUYENHOMNAY FROM USERS";
                            Cursor cursor = userHelper.GetData(query);
                            int time = 0;
                            if (cursor.getCount() > 0) {
                                while (cursor.moveToNext()) {
                                    time = cursor.getInt(0);
                                    time = time + DetailExerciseActivity.mainExercise.getDuration();
                                    query = "UPDATE USERS SET TAPLUYENHOMNAY = " + Integer.toString(time);
                                    userHelper.QueryData(query);
                                    CustomToast.makeText(context, "Hoàn tất bài tập", Toast.LENGTH_SHORT, 1).show();
                                    DetailExerciseActivity.isDone = true;
                                }
                            } else {
                                Log.i("NO USER", "Fail");
                            }


                        } else {
                            type2ViewHolder.doneBtn.setEnabled(false);
                            type2ViewHolder.doneBtn.setBackgroundResource(R.drawable.ic_done_disable);
                            startRelaxTimer(temp);
                        }

                    }
                });
                if (smallExercise.getExerciseType() == 0) {
                    if (countDownTimer == null) {
                        type2ViewHolder.doneBtn.setEnabled(false);
                        type2ViewHolder.doneBtn.setBackgroundResource(R.drawable.ic_done_disable);
                    }
                    type2ViewHolder.timeTextView.setText(Integer.toString(smallExercise.getExerciseDuration()) + " giây");
                    totalTimeInSeconds = smallExercise.getExerciseDuration();
                    type2ViewHolder.pb.setMax(totalTimeInSeconds * 1000);
                    if (countDownTimer == null) {
                        startTimer(type2ViewHolder.pb, type2ViewHolder.doneBtn);
                    } else {

                    }
                } else {
                    type2ViewHolder.pb.setProgress(0);
                    type2ViewHolder.doneBtn.setEnabled(true);
                    type2ViewHolder.doneBtn.setBackgroundResource(R.drawable.ic_done_foreground);
                    type2ViewHolder.timeTextView.setText(Integer.toString(smallExercise.getExerciseRepetitions()) + " lần");
                }
                String variableValue = smallExercise.getImageFileName();
                type2ViewHolder.imageView.setImageResource(activity.getResources().getIdentifier(variableValue, "drawable", activity.getPackageName()));
//                type2ViewHolder.pb.setProgress(50);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type: " + holder.getItemViewType());
        }
    }

    @Override
    public int getItemCount() {
        return smallExercises.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (index == position) {
            return 1;
        }
        return 0;
    }


    private class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView nameTextView, timeTextView;
        LinearLayout linear;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            linear = itemView.findViewById(R.id.linear);
        }
    }

    public class ViewHolderActivated extends RecyclerView.ViewHolder {
        TextView nameTextView, timeTextView;
        ImageView imageView;
        ImageButton imageButton;
        ProgressBar pb;
        Button doneBtn;
        public ViewHolderActivated(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.exerciseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            imageView = itemView.findViewById(R.id.imageView2);
            imageButton = itemView.findViewById(R.id.imageButton);
            pb = itemView.findViewById(R.id.progressBar);
            doneBtn = itemView.findViewById(R.id.doneBtn);
        }

    }

    private void startTimer(ProgressBar progressBar, Button doneBtn) {
        new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 > 4)
                    CustomToast.makeText(context, "Bắt đầu trong " + Long.toString(millisUntilFinished / 1000 - 4), Toast.LENGTH_SHORT, 1).show();
            }

            @Override
            public void onFinish() {
                countDownTimer = new CountDownTimer(totalTimeInSeconds * 1000, intervalInSeconds) {
                    public void onTick(long millisUntilFinished) {
                        progressBar.setProgress((totalTimeInSeconds * 1000) - Integer.parseInt(Long.toString(millisUntilFinished)));
//                Log.i("Timer", "tăng 1 giây");
//                textViewTimer.setText(String.valueOf(secondsRemaining));
                    }

                    public void onFinish() {
                        progressBar.setProgress(totalTimeInSeconds * 1000);
                        doneBtn.setEnabled(true);
                        doneBtn.setBackgroundResource(R.drawable.ic_done_foreground);
//                textViewTimer.setText("Hết giờ!");
                    }
                };
                countDownTimer.start();
            }
        }.start();
    }

    private void startRelaxTimer(int temp) {
//        int temp = index;
//        index = -1;
//        notifyDataSetChanged();
        CountDownTimer countDownTimer = new CountDownTimer(totalRelaxSeconds * 1000, intervalInSeconds * 1000) {
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000;
                DetailExerciseActivity.relaxTV.setText(Integer.toString(secondsRemaining) + " giây");
//                progressBar.setProgress(totalTimeInSeconds - secondsRemaining);
//                Log.i("Timer", "tăng 1 giây");
//                textViewTimer.setText(String.valueOf(secondsRemaining));
            }

            public void onFinish() {
                DetailExerciseActivity.relaxTV.setText(Integer.toString(15) + " giây");
                index = temp + 1;
                notifyItemChanged(index);
            }
        };

        countDownTimer.start();
    }

    public interface onItemClick {
        void onSmallItemClick(int position);
    }
}
