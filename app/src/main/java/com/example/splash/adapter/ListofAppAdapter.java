package com.example.splash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.splash.R;

import java.util.List;

public class ListofAppAdapter extends RecyclerView.Adapter<ListofAppAdapter.MyViewHolder> {
    private List<Model_Saify> appList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView app_icon;
        public TextView app_name;
        public ImageView status;

        public MyViewHolder(View view) {
            super(view);
            this.app_icon = (ImageView) view.findViewById(R.id.appLogo);
            this.app_name = (TextView) view.findViewById(R.id.appName);
            this.status = (ImageView) view.findViewById(R.id.appStatus);
        }
    }

    public ListofAppAdapter(List<Model_Saify> list) {
        this.appList = list;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.apps, viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Model_Saify model = this.appList.get(i);
        myViewHolder.app_icon.setImageDrawable(model.getIcon());
        myViewHolder.app_name.setText(model.getName());
        myViewHolder.status.setImageResource(model.getLocked());
    }

    public int getItemCount() {
        return this.appList.size();
    }
}
