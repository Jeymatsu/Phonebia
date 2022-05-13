package com.example.splash.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.splash.R;
import com.example.splash.adapter.AppList_Saify;

import java.util.List;

public class AppAdapter_saify extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<AppList_Saify> listStorage;

    public long getItemId(int i) {
        return (long) i;
    }

    public AppAdapter_saify(Context context, List<AppList_Saify> list) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listStorage = list;
    }

    public int getCount() {
        return this.listStorage.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = this.layoutInflater.inflate(R.layout.apps, viewGroup, false);
            viewHolder.textInListView = (TextView) view2.findViewById(R.id.appName);
            viewHolder.imageInListView = (ImageView) view2.findViewById(R.id.appLogo);
            viewHolder.lockInListView = (ImageView) view2.findViewById(R.id.appStatus);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textInListView.setText(this.listStorage.get(i).getName());
        viewHolder.imageInListView.setImageDrawable(this.listStorage.get(i).getIcon());
        viewHolder.lockInListView.setImageResource(this.listStorage.get(i).getLocked());
        return view2;
    }

    static class ViewHolder {
        ImageView imageInListView;
        ImageView lockInListView;
        TextView textInListView;

        ViewHolder() {
        }
    }
}
