package com.example.dailyhealth;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class SleepManagement extends AppCompatActivity {

    private int dayColor ;
    private  int nightColor ;

    private YoYo.YoYoString pulseAnimation;
    private TextView textTargetSleep, textActualSleep, textAlarmTime;
    private NumberPicker numberPickerAlarmTime;
    private CheckBox checkboxReminder;
    private RelativeLayout btnReminder, btnSetAlarm, btnSetGoal;
    private TextView textClock;
    private Toolbar toolbar;
    private LinearLayout setting;
    private boolean isSleeping = false;
    private boolean isButtonHeld = false;
    private ConstraintLayout mainLayout;
    private SeekBar progressBar;
    private long buttonHeldStartTime = 0;
    private long pressStartTime = 0;
    private Handler handler;

    private Handler timeHandler = new Handler();
    private Runnable updateTimeRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_management);

        // Initialize views
        progressBar = findViewById(R.id.seekBar);
        mainLayout = findViewById(R.id.mainLayout);
        textTargetSleep = findViewById(R.id.textTargetSleep);
        textActualSleep = findViewById(R.id.textActualSleep);
        textAlarmTime = findViewById(R.id.textAlarmTime);
        btnReminder = findViewById(R.id.reminder);
        btnSetAlarm = findViewById(R.id.alarmSetting);
        btnSetGoal = findViewById(R.id.goalSetting);
        setting = findViewById(R.id.layoutSettings);
        toolbar = findViewById(R.id.toolbar);
        checkboxReminder = findViewById(R.id.checkbox);
        // Set initial values
        textTargetSleep.setText("Mục tiêu ~ 8 giờ");
        textActualSleep.setText("Thời gian ngủ ~ 7 giờ 30 phút");
        textAlarmTime.setText("Giờ báo thức: 8:00 AM");

        // Initialize dayColor and nightColor here
        dayColor = getResources().getColor(R.color.lighter_main_green);
        nightColor = getResources().getColor(android.R.color.black);

        // Set up NumberPicker for hour and minute
        NumberPicker hourPicker = findViewById(R.id.hourPicker);
        NumberPicker minutePicker = findViewById(R.id.minutePicker);

        // Set max and min values for hour and minute pickers
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        // Set NumberPicker's wrap selector wheel behavior
        hourPicker.setWrapSelectorWheel(true);
        minutePicker.setWrapSelectorWheel(true);

        // nSet NumberPicker's value
        String[] hours = new String[24];
        String[] minutes = new String[60];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d", i);
        }
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }

        // Set clock
        // Set onClickListener for btnSetAlarm
        btnSetAlarm.setOnClickListener(v -> setAlarm());

        btnReminder.setOnClickListener(v -> {
            // Change the value of checkboxReminder when btnReminder is clicked
            checkboxReminder.setChecked(!checkboxReminder.isChecked());
        });

        // Thiết lập giá trị cho NumberPicker giờ và phút
        hourPicker.setDisplayedValues(hours);
        minutePicker.setDisplayedValues(minutes);
        // Set onValueChangedListener for NumberPicker to update textAlarmTime
        hourPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateAlarmTimeText());
        minutePicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateAlarmTimeText());
         handler = new Handler();


        Runnable longPressRunnable = new Runnable() {
            @Override
            public void run() {
                // Nếu vẫn giữ nút sau 3 giây và trạng thái chưa là "Ngủ" thì chuyển trạng thái
                if (!isSleeping && isButtonHeld) {
                    isSleeping = true;
                    findViewById(R.id.seekBar).setVisibility(View.VISIBLE);
                    animateToNightMode();
                    ((TextView) findViewById(R.id.btnSleep)).setText("Thức dậy");
                    // hiện ProgressBar khi nút được ấn
                    findViewById(R.id.seekBar).setVisibility(View.VISIBLE);
                }
            }
        };

        SeekBar seekBarTimeHeld = findViewById(R.id.seekBar);
        seekBarTimeHeld.setOnTouchListener((v, event) -> true); // Disable touch events on the SeekBar to prevent user interaction

        findViewById(R.id.btnSleep).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isButtonHeld = true;
                        seekBarTimeHeld.setVisibility(View.VISIBLE);
                        animateButtonHeld();
                        pressStartTime= System.currentTimeMillis();
                        updateSeekBarProgress(); // Bắt đầu cập nhật giá trị của SeekBar khi ấn giữ nút
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isButtonHeld = false;
                        seekBarTimeHeld.setVisibility(View.INVISIBLE);
                        // Đặt giá trị SeekBar về 0 khi thả nút
                        seekBarTimeHeld.setProgress(0);
                        // Hủy handler nếu nút được nhấn và giữ dưới 3 giây
                        handler.removeCallbacks(longPressRunnable);
                        // Kết thúc animation khi thả nút
                        stopButtonHeldAnimation();
                        // Nếu đang trong trạng thái isSleeping = false, chuyển về trạng thái "Ngủ"
                        if (!isSleeping) {
                            isSleeping = true;
                            hideNumberPickerAndShowClock();
                            animateToNightMode();
                            ((TextView) findViewById(R.id.btnSleep)).setText("Thức dậy");
                        } else { // Nếu đang trong trạng thái isSleeping = true, chuyển về trạng thái "Thức dậy"
                            // Check if the button was held for at least 3 seconds
                            long pressDuration = System.currentTimeMillis() - pressStartTime;
                            if (pressDuration >= 2000) {
                                // Transition to the "awake" state if the button was held for 3 seconds
                                    isSleeping = false;
                                    showNumberPickerAndHideClock();
                                    animateToDayMode();
                                    ((TextView) findViewById(R.id.btnSleep)).setText("Ngủ");
                                    if (pulseAnimation != null) {
                                    pulseAnimation.stop();
                                }

                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void updateClockTime() {
        textClock.setText(getCurrentTime());
    }
    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
    }
    private void startUpdatingTime() {
        updateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateClockTime();
                timeHandler.postDelayed(this, 60000); // Cập nhật sau mỗi 1 phút (60.000 ms)
            }
        };
        timeHandler.post(updateTimeRunnable);
    }

    // Method to update textAlarmTime based on hour and minute picked
    private void updateAlarmTimeText() {
        NumberPicker hourPicker = findViewById(R.id.hourPicker);
        NumberPicker minutePicker = findViewById(R.id.minutePicker);

        int hour = hourPicker.getValue();
        int minute = minutePicker.getValue();

        // Format hour and minute to display in textAlarmTime
        String amPm = (hour < 12) ? "AM" : "PM";
        int formattedHour = (hour == 0 || hour == 12) ? 12 : hour % 12;
        String alarmTime = String.format("Giờ báo thức: %d:%02d %s", formattedHour, minute, amPm);

        // Set textAlarmTime with the formatted alarm time
        textAlarmTime.setText(alarmTime);

        // Tính thời gian ngủ thực tế từ thời gian bây giờ đến thời gian thức dậy
        Calendar now = Calendar.getInstance();
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.HOUR_OF_DAY, hour);
        alarmCalendar.set(Calendar.MINUTE, minute);
        alarmCalendar.set(Calendar.SECOND, 0);

        if (alarmCalendar.before(now)) {
            alarmCalendar.add(Calendar.DAY_OF_MONTH, 1); // Nếu thời gian thức dậy trước thời gian bây giờ, thì cộng thêm 1 ngày
        }

        long timeInMillis = alarmCalendar.getTimeInMillis() - now.getTimeInMillis();
        long hoursToSleep = timeInMillis / (60 * 60 * 1000);
        long minutesToSleep = (timeInMillis % (60 * 60 * 1000)) / (60 * 1000);

        // Format thời gian ngủ thực tế và hiển thị lên textActualSleep
        String actualSleepTime = String.format("Thời gian ngủ ~ %d giờ %02d phút", hoursToSleep, minutesToSleep);
        textActualSleep.setText(actualSleepTime);
        // Đặt màu sắc cho TextView textActualSleep dựa vào giờ ngủ
        if (hoursToSleep < 7) {
            textActualSleep.setTextColor(getResources().getColor(android.R.color.holo_red_dark)); // Màu đỏ
        } else if (hoursToSleep >= 9) {
            textActualSleep.setTextColor(getResources().getColor(android.R.color.holo_orange_light)); // Màu vàng
        } else {
            textActualSleep.setTextColor(getResources().getColor(android.R.color.white)); // Màu trắng
        }
    }
    // Hàm bắt đầu animation khi đang giữ nút
    private void animateButtonHeld() {
        if (pulseAnimation == null || !pulseAnimation.isRunning()) {
            pulseAnimation = YoYo.with(Techniques.Pulse)
                    .duration(1000)
                    .repeat(YoYo.INFINITE)
                    .playOn(findViewById(R.id.btnSleep));
        }
    }

    // Hàm kết thúc animation khi thả nút
    private void stopButtonHeldAnimation() {
        YoYo.with(Techniques.FadeIn)
                .duration(300)
                .playOn(findViewById(R.id.btnSleep));
    }

    private void updateSeekBarProgress() {
        SeekBar seekBarTimeHeld = findViewById(R.id.seekBar);
        if (isButtonHeld) {
            // Lấy thời gian hiện tại
            long currentTime = System.currentTimeMillis();
            // Tính thời gian đã giữ nút (elapsedTime)
            long elapsedTime = currentTime - pressStartTime;
            // Tính phần trăm hoàn thành của SeekBar dựa trên thời gian đã giữ nút và thời gian giữ nút tối đa
            int progress = (int) (elapsedTime * 100 / 2000);
            // Giới hạn giá trị progress trong khoảng 0 đến 100
            progress = Math.min(progress, 100);
            // Cập nhật giá trị của SeekBar
            seekBarTimeHeld.setProgress(progress);
            // Nếu chưa đạt 100%, tiếp tục cập nhật giá trị SeekBar
            if (progress < 100) {
                handler.postDelayed(this::updateSeekBarProgress, 100);
            }
        }
    }
    private void setAlarm() {
        // Lấy thời gian từ NumberPicker
        NumberPicker hourPicker = findViewById(R.id.hourPicker);
        NumberPicker minutePicker = findViewById(R.id.minutePicker);
        int hour = hourPicker.getValue();
        int minute = minutePicker.getValue();

        // Tạo Intent để mở ứng dụng đồng hồ
        Intent alarmClockIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarmClockIntent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        alarmClockIntent.putExtra(AlarmClock.EXTRA_MINUTES, minute);

        // Kiểm tra xem điện thoại có hỗ trợ ứng dụng đồng hồ không
        if (alarmClockIntent.resolveActivity(getPackageManager()) != null) {
            // Mở ứng dụng đồng hồ và đặt báo thức
            startActivity(alarmClockIntent);
        } else {
            // Nếu không tìm thấy ứng dụng đồng hồ, thông báo cho người dùng
            Toast.makeText(this, "Không tìm thấy ứng dụng đồng hồ trên thiết bị của bạn. Hãy tự đặt báo thức nhé!", Toast.LENGTH_SHORT).show();
        }
    }

    // Ẩn NumberPicker và hiển thị TextView với giờ và phút đã chọn
    private void hideNumberPickerAndShowClock() {
        // Ẩn NumberPicker
        NumberPicker hourPicker = findViewById(R.id.hourPicker);
        NumberPicker minutePicker = findViewById(R.id.minutePicker);
        TextView dot = findViewById(R.id.dotdot);


        hourPicker.setVisibility(View.GONE);
        minutePicker.setVisibility(View.GONE);
        dot.setVisibility(View.GONE);
//        hourPicker.animate().alpha(0f).setDuration(1000).withEndAction(() -> hourPicker.setVisibility(View.GONE)).start();;
//        minutePicker.animate().alpha(0f).setDuration(1000).withEndAction(() -> minutePicker.setVisibility(View.GONE)).start();
//        dot.animate().alpha(0f).setDuration(1000).withEndAction(() -> dot.setVisibility(View.GONE)).start();;

        // Lấy giờ và phút từ NumberPicker
        int hour = hourPicker.getValue();
        int minute = minutePicker.getValue();

        // Tạo mới hoặc lấy TextView textClock nếu đã tồn tại trong layout
        if (textClock == null) {
            textClock = new TextView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.addRule(RelativeLayout.BELOW, R.id.alarmSetting); // Đặt textClock dưới NumberPicker
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // Đặt textClock ở giữa theo chiều ngang
            textClock.setLayoutParams(layoutParams);
            textClock.setTextColor(Color.WHITE);
            textClock.setTextSize(TypedValue.COMPLEX_UNIT_SP, 45);
            textClock.setGravity(View.TEXT_ALIGNMENT_CENTER);
            ((LinearLayout) findViewById(R.id.numberPickerAlarmTime)).addView(textClock); // Thay R.id.yourRelativeLayout bằng ID của RelativeLayout trong layout của bạn
        }

        // Hiển thị TextView với giờ và phút đã chọn

        startUpdatingTime();
        textClock.setVisibility(View.VISIBLE);
        textClock.animate().alpha(1f).setDuration(1000).start();
    }

    // Hiển thị lại NumberPicker và ẩn TextView với giờ và phút đã chọn
    private void showNumberPickerAndHideClock() {
        NumberPicker hourPicker = findViewById(R.id.hourPicker);
        NumberPicker minutePicker = findViewById(R.id.minutePicker);
        TextView dot = findViewById(R.id.dotdot);

        hourPicker.setVisibility(View.VISIBLE);
        minutePicker.setVisibility(View.VISIBLE);
        dot.setVisibility(View.VISIBLE);
        textClock.setVisibility(View.GONE);
    }

    // Thực hiện chuyển đổi màu nền của màn hình thành đêm trong 1 giây
    private void animateToNightMode() {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), dayColor, nightColor);
        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(animator -> {
            int color = (int) animator.getAnimatedValue();
            mainLayout.setBackgroundColor(color);
            toolbar.setBackgroundColor(color);
            setting.setVisibility(View.GONE);
        });
        colorAnimation.start();
    }

    // Thực hiện chuyển đổi màu nền của màn hình về màu trắng trong 1 giây
    private void animateToDayMode() {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), nightColor, dayColor);
        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(animator -> {
            int color = (int) animator.getAnimatedValue();
            mainLayout.setBackgroundColor(color);
            mainLayout.setBackgroundColor(color);
            toolbar.setBackgroundColor(color);
            setting.setVisibility(View.VISIBLE);
        });
        colorAnimation.start();
    }
}