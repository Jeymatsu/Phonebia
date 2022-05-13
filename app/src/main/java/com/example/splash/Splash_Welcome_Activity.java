package com.example.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.splash.Services.BackgroundManager;


public class Splash_Welcome_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_welcome);
        BackgroundManager.getInstance().init(this).startService(Splash_Welcome_Activity.class);

        Handler handler= new Handler();
        Runnable run=new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_Welcome_Activity.this, slidefocus.class);
                startActivity(intent);

            }
        };handler.postDelayed(run,4000);

    }
}