package com.example.splash.Services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.example.splash.Db.Apply_password_on_AppDatabase;
import com.example.splash.LockScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class BackgroundServices extends Service {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static int flag;
    static int flag2;
    public int counter = 0;
    String current_app = "";

    /* renamed from: db */
    Apply_password_on_AppDatabase f77db = new Apply_password_on_AppDatabase(this);
    Context mContext;
    LockScreen obj = new LockScreen();
    private BroadcastReceiver receiver;
    private Timer timer;
    private TimerTask timerTask;
    BackgroundManager backgroundManager;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        if (Build.VERSION.SDK_INT > 26) {
            startMyOwnForeground();
        } else {
            startForeground(1, new Notification());
        }
    }

    @RequiresApi(26)
    private void startMyOwnForeground() {
        NotificationChannel notificationChannel = new NotificationChannel("example.permanence", "Background Service", NotificationManager.IMPORTANCE_NONE);
        notificationChannel.setLightColor(-16776961);
        notificationChannel.setLockscreenVisibility(0);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(notificationChannel);
        startForeground(2, new NotificationCompat.Builder(this, "example.permanence").setOngoing(true).setContentTitle("App is running in background").setPriority(1).setCategory(NotificationCompat.CATEGORY_SERVICE).build());
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        startTimer();
        return START_STICKY;
    }

    public void onDestroy() {

        super.onDestroy();
       // stoptimertask();
       // Intent intent = new Intent();
      //  intent.setAction("restartservice");
      //  intent.setClass(this, RestartService.class);
      //  sendBroadcast(intent);
    }

    public void startTimer() {
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            public void run() {
                if (BackgroundServices.flag == 0) {
                    Cursor allData = BackgroundServices.this.f77db.getAllData();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    while (allData.moveToNext()) {
                        Log.i("Count", "=========  " + BackgroundServices.this.printForegroundTask());
                        arrayList.add(allData.getString(0));
                        arrayList2.add(allData.getString(1));
                    }
                    if (arrayList.contains(BackgroundServices.this.printForegroundTask())) {
                        BackgroundServices.flag = 1;
                        BackgroundServices backgroundServices = BackgroundServices.this;
                        backgroundServices.current_app = backgroundServices.printForegroundTask();
                        Intent intent = new Intent(BackgroundServices.this.mContext, LockScreen.class);
                        intent.putExtra("pack", BackgroundServices.this.printForegroundTask());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        BackgroundServices.this.mContext.startActivity(intent);
                    }
                }
                if (!BackgroundServices.this.printForegroundTask().equals("com.example.OfficialMuslimart") && BackgroundServices.flag2 == 0 && !BackgroundServices.this.printForegroundTask().equals(BackgroundServices.this.current_app)) {
                    BackgroundServices.flag = 0;
                }
                if (BackgroundServices.this.printForegroundTask().equals("com.example.OfficialMuslimart")) {
                    BackgroundServices.flag = 2;
                }
            }
        };
        this.timer.schedule(this.timerTask, 0, 100);
    }

    public void stoptimertask() {
        Timer timer2 = this.timer;
        if (timer2 != null) {
            timer2.cancel();
            this.timer = null;
        }
    }

    /* access modifiers changed from: private */
    public String printForegroundTask() {
        if (Build.VERSION.SDK_INT < 21) {
            return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses().get(0).processName;
        }
        long currentTimeMillis = System.currentTimeMillis();
        List<UsageStats> queryUsageStats = ((UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE)).queryUsageStats(0, currentTimeMillis - 1000000, currentTimeMillis);
        if (queryUsageStats == null || queryUsageStats.size() <= 0) {
            return "NULL";
        }
        TreeMap treeMap = new TreeMap();
        for (UsageStats next : queryUsageStats) {
            treeMap.put(Long.valueOf(next.getLastTimeUsed()), next);
        }
        if (!treeMap.isEmpty()) {
            return ((UsageStats) treeMap.get(treeMap.lastKey())).getPackageName();
        }
        return "NULL";
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
