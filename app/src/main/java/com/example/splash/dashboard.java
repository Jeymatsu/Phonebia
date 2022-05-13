package com.example.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
}

   public void OpenSleepModule(View view){
       Intent intent= new Intent(dashboard.this,sleepmodule.class);
       startActivity(intent);


   }
    public void OpenStudyModule(View view){
        Intent intent= new Intent(dashboard.this,studymodule.class);
        startActivity(intent);


    }
    public void OpenWorkModule(View view){
        Intent intent= new Intent(dashboard.this,workmodule.class);
        startActivity(intent);


    }
    public void OpenFocusModule(View view){
        Intent intent= new Intent(dashboard.this, focusmodule.class);
        startActivity(intent);


    }

    public void openLockApps(View v){
         Intent intent= new Intent(dashboard.this,installedApps.class);
        startActivity(intent);

    }
}