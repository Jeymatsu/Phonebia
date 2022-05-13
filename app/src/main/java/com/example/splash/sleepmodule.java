package com.example.splash;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;

import com.example.splash.Services.BackgroundManager;
import com.example.splash.Services.BackgroundServices;

import java.util.Locale;

public class sleepmodule extends AppCompatActivity {
    TextView txtsleeptime;
    TextView txttimer;
    int hours;
    int minute;
    Button btnStart;
    Button btnSet;
    EditText edtset;
    long mStartTimeInMillis;
    long mTimeLeftInMillis;
    TextView txtCountdown;
    long mEndTime;
    CountDownTimer countDownTimer;
    BackgroundServices backgroundServices;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepmodule);
        btnStart=findViewById(R.id.btnStart);
        txtCountdown=findViewById(R.id.text_view_countdown);
        edtset=findViewById(R.id.edit_text_input);
        btnSet=findViewById(R.id.button_set);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=edtset.getText().toString();
                if(input.length()==0){
                    Toast.makeText(sleepmodule.this, "Field Can't be empty", Toast.LENGTH_SHORT).show();
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(sleepmodule.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                edtset.setText("");

            }
        });





        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startTimer();
             btnSet.setVisibility(View.GONE);
             edtset.setVisibility(View.GONE);
            }
        });

    }

    private void setTime(long milliseconds) {

        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();

    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        txtCountdown.setText(timeLeftFormatted);
    }
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                Toast.makeText(sleepmodule.this, "You can Access The APP", Toast.LENGTH_LONG).show();
                stopService();
            }
        }.start();


    }

    public void OpenApplock(View view){
        Intent intent= new Intent(sleepmodule.this, MainActivity.class);
        startActivity(intent);
    }
    public void notification(){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.logophone).setContentTitle("Phonebia").setContentText("" +
                "Hello,You can now Access the Apps.Thank you for Choosing Phonebia");
        Intent notificationIntent=new Intent(sleepmodule.this,sleepmodule.class);
        PendingIntent contentIntent=PendingIntent.getActivity(sleepmodule.this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());

    }
    public void stopService(){
        BackgroundManager.getInstance().init(this).stopService(BackgroundServices.class);


    }


}