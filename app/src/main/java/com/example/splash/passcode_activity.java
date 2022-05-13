package com.example.splash;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



public class passcode_activity extends AppCompatActivity {
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        password=findViewById(R.id.password);
        //BackgroundManager.getInstace().init(this).startService();

        init1coneApp();


    }

    private void init1coneApp() {
        if(getIntent().getStringExtra("Broadcast_Receiver")!=null){
            ImageView icon=findViewById(R.id.appLogo);
            String currentApp=new utils(this).getLastApp();
            ApplicationInfo applicationInfo=null;
            try {
                applicationInfo=getPackageManager().getApplicationInfo(currentApp,0);
            } catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
            icon.setImageDrawable(applicationInfo.loadIcon(getPackageManager()));

        }
    }

    private void startAct(){

        if(getIntent().getStringExtra("Broadcast_Receiver")==null){
            startActivity(new Intent(this, installedApps.class));
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        startCurrentHomePackage();
        finish();
        super.onBackPressed();
    }

    public void startCurrentHomePackage(){
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo=getPackageManager().resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY);
        ActivityInfo activityInfo=resolveInfo.activityInfo;
        ComponentName componentName= new ComponentName(activityInfo.applicationInfo.packageName,activityInfo.name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
        new utils(this).clearLastApp();
    }
    public void open(View v){
        confirmPassword();

    }

    public void confirmPassword (){
        String passwordcorrect="1234";
        String inputPassword=password.getText().toString();
        if(passwordcorrect.equals(inputPassword)){
            startAct();

        }

        finish();


    }

}
