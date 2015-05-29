package com.logotet.androsvis;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.model.Prilog;

import java.util.List;

/**
 * Created by boban on 5/21/15.
 */
public class TackaPrilogAdapter extends BaseAdapter {
    private static final String TAG = "TackaPrilogAdapter";

    private Activity activity;
    private LayoutInflater inflater;
    private List<Prilog> prilozi;

    public TackaPrilogAdapter(Activity activity) {
//        Log.w(TAG,"construcor started");
        this.activity = activity;
        prilozi = AllStatic.currentTacka.getPrilozi();
//        Log.w(TAG, "construcor end " + prilozi.size());
    }

    @Override
    public int getCount() {
        return  prilozi.size();
    }

    @Override
    public Object getItem(int position) {
        return prilozi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.prilozi, null);

        TextView naziv = (TextView) convertView.findViewById(R.id.prilog_tv_naziv);
        ImageView ikona = (ImageView) convertView.findViewById(R.id.prilog_iv_ikona);

        Prilog prilog = prilozi.get(position);
        naziv.setText(prilog.getNaziv());
        ikona.setImageResource(prilog.getResourceId());

        return convertView;
    }
}
