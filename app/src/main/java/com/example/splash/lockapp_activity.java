package com.example.splash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splash.Db.Apply_password_on_AppDatabase;
import com.example.splash.Db.Password_Database;
import com.example.splash.adapter.AppAdapter;
import com.example.splash.adapter.AppItem;
import com.example.splash.adapter.AppList_Saify;
import com.example.splash.adapter.ListofAppAdapter;
import com.example.splash.adapter.Model_Saify;
import com.example.splash.adapter.RecyclerTouchListener;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class lockapp_activity extends AppCompatActivity {

    /* access modifiers changed from: private */
    public List<Model_Saify> appList = new ArrayList();

    /* renamed from: db */
    Apply_password_on_AppDatabase f83db = new Apply_password_on_AppDatabase(this);
    List<String> lock = new ArrayList();
    /* access modifiers changed from: private */
    public ListofAppAdapter mAdapter;
    Model_Saify model2;
    AppAdapter appAdapter;
    String pass = "";
    Password_Database pass_db = new Password_Database(this);
    private RecyclerView recyclerView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.lockapp_activity);
        final ProgressDialog[] progressDialogArr = new ProgressDialog[1];


        Cursor allData = this.f83db.getAllData();
        Cursor allData2 = this.pass_db.getAllData();
        while (allData.moveToNext()) {
            this.lock.add(allData.getString(0));
        }
        while (allData2.moveToNext()) {
            this.pass = allData2.getString(0);
        }
        this.recyclerView = (RecyclerView) findViewById(R.id.appppp_list);
        this.mAdapter = new ListofAppAdapter(this.appList);
        //this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.mAdapter);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getInstalledApps();
        recyclerView.setAdapter(mAdapter);
        this.mAdapter.notifyDataSetChanged();
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), this.recyclerView, new RecyclerTouchListener.ClickListener() {
          public void onLongClick(View view, int i) {
            }

            public void onClick(View view, int i) {
                Model_Saify model = (Model_Saify) lockapp_activity.this.appList.get(i);
                if (model.getLocked() == R.mipmap.unlock) {
                    lockapp_activity.this.f83db.deleteData(model.getPackage_name());
                    Toast.makeText(lockapp_activity.this, "App Locked", Toast.LENGTH_LONG).show();
                    model.setLocked(R.mipmap.lock);
                } else {
                    lockapp_activity lockapp_activity = lockapp_activity.this;
                    lockapp_activity.model2 = model;
                    AlertDialog.Builder builder = new AlertDialog.Builder(lockapp_activity);
                    builder.setCancelable(false);
                    builder.setTitle((CharSequence) "Select your option:");
                    builder.setItems(new CharSequence[]{"Lock App", "Do nothing"}, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0) {
                                Toast.makeText(lockapp_activity, "App Locked Successfuly", Toast.LENGTH_SHORT).show();
                                lockapp_activity.this.f83db.insertData(lockapp_activity.this.model2.getPackage_name(), lockapp_activity.this.pass);


                            }
                        }
                    });
                    builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            model.setLocked(R.mipmap.unlock);
                            lockapp_activity.this.f83db.deleteData(lockapp_activity.this.model2.getPackage_name());
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                lockapp_activity.this.mAdapter.notifyDataSetChanged();
            }
        }));
    }

    private List<AppList_Saify> getInstalledApps() {
        ArrayList arrayList = new ArrayList();
        List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);
        PackageManager pk = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory((Intent.CATEGORY_LAUNCHER));
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            PrintStream printStream = System.out;
            printStream.println("size = " + installedPackages.size());
            if (this.lock.contains(installedPackages.get(i).packageName)) {
                if (!isSystemPackage(packageInfo)) {
                    this.appList.add(new Model_Saify(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(), packageInfo.applicationInfo.loadIcon(getPackageManager()), R.mipmap.lock, installedPackages.get(i).packageName));
                }
            } else if (!isSystemPackage(packageInfo)) {
                this.appList.add(new Model_Saify(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(), packageInfo.applicationInfo.loadIcon(getPackageManager()), R.mipmap.unlock, installedPackages.get(i).packageName));
            }
        }
        return arrayList;

    }

    private boolean isSystemPackage(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & 1) != 0;
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppAdapter appAdapter = new AppAdapter(this, getAllApps());
       // recyclerView.setAdapter(appAdapter);



    }

    private List<AppList_Saify> getAllApps() {
        List<AppList_Saify> results = new ArrayList<>();
        PackageManager pk = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory((Intent.CATEGORY_LAUNCHER));

        List<ResolveInfo> resolveInfoList = pk.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
           // results.add(new AppList_Saify(activityInfo.loadIcon(pk), activityInfo.loadLabel(pk).toString(), activityInfo.packageName),lock);


        }


        return results;

    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
