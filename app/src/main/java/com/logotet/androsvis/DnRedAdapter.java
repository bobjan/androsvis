package com.logotet.androsvis;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.model.Tacka;

import java.util.List;

/**
 * Created by boban on 5/21/15.
 */
public class DnRedAdapter extends BaseAdapter {
    private static final String TAG = "DnRedAdapter";

    private Activity activity;
    private LayoutInflater inflater;
    private List<Tacka> dnred;

    public DnRedAdapter(Activity activity) {
//        Log.w(TAG,"construcor started");
        this.activity = activity;
        dnred = AllStatic.currentSednica.getDnevnired();
//        Log.w(TAG,"construcor end " + dnred.size());
    }

    @Override
    public int getCount() {
        return  dnred.size();
    }

    @Override
    public Object getItem(int position) {
        return dnred.get(position);
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
            convertView = inflater.inflate(R.layout.tackadnreda, null);

        TextView redbroj = (TextView) convertView.findViewById(R.id.tdr_tv_redniBrojTacke);
        TextView podbroj = (TextView) convertView.findViewById(R.id.tdr_tv_podbrojTacke);
        TextView naslovTacke = (TextView) convertView.findViewById(R.id.tdr_tv_naslovTacke);
        TextView izvestilac = (TextView) convertView.findViewById(R.id.tdr_tv_izvestilac);

        LinearLayout layoutPrilozi = (LinearLayout)convertView.findViewById(R.id.tdr_ll_imgprilozi);

        Tacka tacka = dnred.get(position);
//        Log.w(TAG,"broj priloga  " + lista.size());
//        Log.w(TAG, "tacka " + tacka.getRednibroj() + "-" + tacka.getPodbroj());
        redbroj.setText((tacka.getPodbroj() == 0) ? tacka.getRednibroj() + "" : "");
        podbroj.setText((tacka.getPodbroj() == 0) ? "" : "*");
        naslovTacke.setText(tacka.getNaziv());
        izvestilac.setText(tacka.getIzvestilac());
        if(tacka.getIzvestilac().trim().length() == 0)
            izvestilac.setHeight(0);



        List lista = tacka.getPrilozi();
        layoutPrilozi.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(1, 0, 4, 1);

        for(int i = 0; i < lista.size(); i++) {
//            Log.w(TAG, "index = " + i);
            ImageView slicica = new ImageView(convertView.getContext());
            slicica.setBackgroundResource(R.drawable.malikrug);
            slicica.setLayoutParams(lp);

            layoutPrilozi.addView(slicica);
       }

        /*
        *
        * // access your linear layout
LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
// load the xml structure of your row
View child = getLayoutInflater().inflate(R.layout.row);
// now fill the row as you would do with listview
//e.g. (TextView) child.findViewById(...
...
// and than add it
layout.addView(child);
        * */


        return convertView;

    }
}
