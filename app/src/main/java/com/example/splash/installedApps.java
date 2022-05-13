package com.example.splash;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.splash.adapter.AppItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class installedApps extends AppCompatActivity {
    RecyclerView app_list;
    FloatingActionButton btn_ok;
    LinearLayout layout_permisson;
    utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_apps);
        app_list = findViewById(R.id.app_list);
        initView();

    }

    private void initView() {
        app_list.setHasFixedSize(true);
        app_list.setLayoutManager(new LinearLayoutManager(this));

        AppAdapter appAdapter = new AppAdapter(this, getAllApps());
        app_list.setAdapter(appAdapter);
        layout_permisson = findViewById(R.id.layout_permission);


    }

    private List<AppItem> getAllApps() {
        List<AppItem> results = new ArrayList<>();
        PackageManager pk = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory((Intent.CATEGORY_LAUNCHER));

        List<ResolveInfo> resolveInfoList = pk.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            results.add(new AppItem(activityInfo.loadIcon(pk), activityInfo.loadLabel(pk).toString(), activityInfo.packageName));


        }


        return results;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();
        return true;
    }

    public void setPermission(View v) {
        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));


    }

    @Override
    protected void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            if (utils.checkPermission(this)) {
                layout_permisson.setVisibility(View.GONE);
            } else {
                layout_permisson.setVisibility(View.VISIBLE);

            }
        super.onResume();
    }



    public void startCurrentHomePackage() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        ComponentName componentName = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
        new utils(this).clearLastApp();
    }
}

