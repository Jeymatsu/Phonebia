package com.example.splash;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.splash.Db.Password_Database;
import com.example.splash.Services.BackgroundServices;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    /* renamed from: cn */
    Context f81cn;

    /* renamed from: db */
    Password_Database f82db = new Password_Database(this);
    Button lockapp;

    Intent mServiceIntent;
    private BackgroundServices mYourService;
    String m_Text = "";
    List<String> pass = new ArrayList();
    Button priv;
    Button update_pass;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_passcode);

        this.lockapp = (Button) findViewById(R.id.btnopen);
        this.update_pass = (Button) findViewById(R.id.updatePassword);
        this.f81cn = this;
        Cursor allData = this.f82db.getAllData();
        while (allData.moveToNext()) {
            this.pass.add(allData.getString(0));
        }
        this.lockapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "First Set Password", Toast.LENGTH_LONG).show();
                    return;
                }
                MainActivity mainActivity = MainActivity.this;
                mainActivity.startActivity(new Intent(mainActivity,lockapp_activity.class));
                MainActivity.this.finish();
            }
        });
        this.update_pass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this.f81cn);
                builder.setTitle("Enter Password");
                final EditText editText = new EditText(MainActivity.this.f81cn);
                editText.setInputType(129);
                builder.setView(editText);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.m_Text = editText.getText().toString();
                        if (MainActivity.this.m_Text.isEmpty()) {
                            Toast.makeText(MainActivity.this, "password can't be empty", Toast.LENGTH_LONG).show();
                            return;
                        }
                        MainActivity.this.f82db.deleteData(MainActivity.this.m_Text).intValue();
                        MainActivity.this.f82db.insertData(MainActivity.this.m_Text);
                        Toast.makeText(MainActivity.this, "password updated successfully ", Toast.LENGTH_LONG).show();
                        MainActivity.this.startActivity(new Intent(MainActivity.this, MainActivity.class));
                        MainActivity.this.finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
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

    /* access modifiers changed from: protected */
    @RequiresApi(api = 19)
    public void onStart() {
        super.onStart();
        if (!isAccessGranted()) {
            new AlertDialog.Builder(this).setTitle("USAGE_STATS Permission").setMessage("Allow USAGE_STATS Permission in Setting").setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
                }
            }).setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).show();
        } else if (this.pass.isEmpty()) {
            this.update_pass.setText("Set Password");
            AlertDialog.Builder builder = new AlertDialog.Builder(this.f81cn);
            builder.setTitle("Enter Password");
            final EditText editText = new EditText(this.f81cn);
            editText.setInputType(129);
            builder.setView(editText);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.m_Text = editText.getText().toString();
                    if (MainActivity.this.m_Text.isEmpty()) {
                        Toast.makeText(MainActivity.this, "password can't be empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    MainActivity.this.f82db.insertData(MainActivity.this.m_Text);
                    MainActivity.this.pass.add(MainActivity.this.m_Text);
                    MainActivity mainActivity = MainActivity.this;
                    Toast.makeText(mainActivity, "password added successfully " + MainActivity.this.m_Text, Toast.LENGTH_LONG).show();
                    MainActivity.this.update_pass.setText("Update Password");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
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

    @RequiresApi(api = 19)
    private boolean isAccessGranted() {
       
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), 0);
            if ((Build.VERSION.SDK_INT > 19 ? ((AppOpsManager) getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats", applicationInfo.uid, applicationInfo.packageName) : 0) == 0) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
