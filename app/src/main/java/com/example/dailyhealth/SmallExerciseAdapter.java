package com.example.dailyhealth;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
//    private Handler handler;

    public SmallExerciseAdapter(Activity activity, ArrayList<SmallExercise> smallExercises) {
        this.activity = activity;
        this.smallExercises = smallExercises;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        DetailExerciseActivity.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SmallExercise smallExercise = smallExercises.get(position);
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
                        int temp = index;
                        index = -1;
                        notifyItemChanged(indexReal);
                        indexReal = indexReal + 1;
                        type2ViewHolder.doneBtn.setEnabled(false);
                        startRelaxTimer(temp);
                    }
                });
                if (smallExercise.getExerciseType() == 0) {
                    type2ViewHolder.doneBtn.setEnabled(false);
                    type2ViewHolder.timeTextView.setText(Integer.toString(smallExercise.getExerciseDuration()) + " giây");
//                    totalTimeInSeconds = smallExercise.getExerciseDuration();
                    type2ViewHolder.pb.setMax(totalTimeInSeconds);
                    startTimer(type2ViewHolder.pb, type2ViewHolder.doneBtn);
                } else {
                    type2ViewHolder.pb.setProgress(0);
                    type2ViewHolder.doneBtn.setEnabled(true);
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
        CountDownTimer countDownTimer = new CountDownTimer(totalTimeInSeconds * 1000, intervalInSeconds * 1000) {
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) millisUntilFinished / 1000;
                progressBar.setProgress(totalTimeInSeconds - secondsRemaining);
//                Log.i("Timer", "tăng 1 giây");
//                textViewTimer.setText(String.valueOf(secondsRemaining));
            }

            public void onFinish() {
                progressBar.setProgress(totalTimeInSeconds);
                doneBtn.setEnabled(true);
//                textViewTimer.setText("Hết giờ!");
            }
        };

        countDownTimer.start();
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
