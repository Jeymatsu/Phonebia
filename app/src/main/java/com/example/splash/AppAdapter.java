package com.example.splash;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.splash.Db.Apply_password_on_AppDatabase;
import com.example.splash.Db.Password_Database;
import com.example.splash.adapter.AppItem;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppViewHolder> {
    private Context mContetx;
    private utils utils;
    Apply_password_on_AppDatabase f83db = new Apply_password_on_AppDatabase(mContetx);
    Password_Database pass_db = new Password_Database(mContetx);
    List<String> lock = new ArrayList();
    String pass = "";

    public AppAdapter(Context mContetx, List<AppItem> appItemList) {
        this.mContetx = mContetx;
        this.appItemList = appItemList;
        this.utils=new utils(mContetx);
    }

    private List<AppItem>  appItemList;

    @NonNull

    @Override
    public AppViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContetx).inflate(R.layout.apps,parent,false);

        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AppViewHolder holder, int position) {
         holder.name_app.setText(appItemList.get(position).getName());
         holder.icone_app.setImageDrawable(appItemList.get(position).getIcone());

         String pk= appItemList.get(position).getPackageName();
         if(utils.isLock(pk)){
             holder.lock_app.setImageResource(R.mipmap.lock);
         }else {
             holder.lock_app.setImageResource(R.mipmap.unlock);

            /* Cursor allData = this.f83db.getAllData();
             Cursor allData2 = this.pass_db.getAllData();
             while (allData.moveToNext()) {
                 this.lock.add(allData.getString(0));
             }
             while (allData2.moveToNext()) {
                 this.pass = allData2.getString(0);
             }
             AppItem appItem;*/
         }
         holder.setListener(new AppOnClickInterface() {
             @Override
             public void selectApp(int pos) {
                 if(utils.isLock(pk)){
                     holder.lock_app.setImageResource(R.mipmap.unlock);
                     utils.unlock(pk);
                     Toast.makeText(mContetx, "App Unlocked Successfully", Toast.LENGTH_SHORT).show();
                 }else {
                     holder.lock_app.setImageResource(R.mipmap.lock);
                     utils.lock(pk);
                     Toast.makeText(mContetx, "App Locked Successfully", Toast.LENGTH_SHORT).show();

                 }


             }
         });



    }

    @Override
    public int getItemCount() {
        return appItemList.size();
    }
}
