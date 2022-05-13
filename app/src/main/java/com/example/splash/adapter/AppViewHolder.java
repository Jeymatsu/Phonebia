package com.example.splash.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splash.AppOnClickInterface;
import com.example.splash.R;

public class AppViewHolder extends RecyclerView.ViewHolder {
    public ImageView icone_app,lock_app;
    public TextView name_app;
    private AppOnClickInterface listener;

    public void setListener(AppOnClickInterface listener) {
        this.listener = listener;
    }

    public AppViewHolder(@NonNull  View itemView) {
        super(itemView);
        icone_app=itemView.findViewById(R.id.appLogo);
        lock_app= itemView.findViewById(R.id.appStatus);
        name_app=itemView.findViewById(R.id.appName);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selectApp(getAdapterPosition());

            }
        });
    }
}
