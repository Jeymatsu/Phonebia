package com.example.splash;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splash.Services.BackgroundManager;
import com.example.splash.Services.BackgroundServices;

public class slidefocus extends AppCompatActivity {
    float x1,y1,x2,y2;
    private BackgroundServices mYourService;
    Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidefocus);
        BackgroundManager.getInstance().init(this).startService(slidefocus.class);

    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 > x2){
                    Intent i = new Intent(slidefocus.this, slidesleep.class);
                    startActivity(i);
                    finish();
            }
            break;
        }
        return false;
    }
    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Context.BIND_EXTERNAL_SERVICE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.mYourService = new BackgroundServices();
        this.mServiceIntent = new Intent(this, this.mYourService.getClass());
        if (isMyServiceRunning(this.mYourService.getClass())) {
            return;
        }
        if (Build.VERSION.SDK_INT > 26) {
            startForegroundService(this.mServiceIntent);
        } else {
            startService(this.mServiceIntent);
        }
    }
}

