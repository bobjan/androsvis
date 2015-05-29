package com.logotet.androsvis;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.model.Sednica;

/**
 * Created by boban on 5/23/15.
 */
public class ArhivaAdapter extends BaseAdapter {
    private static final String TAG = "ArhivaAdapter";
    private Context mContext;
    public ArhivaAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return AllStatic.sednice.size();
    }

    @Override
    public Object getItem(int position) {
        return AllStatic.sednice.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        Sednica sednica = (Sednica) getItem(position);
        Log.w(TAG, position + "...." + sednica.getDatum().toString());
        Resources res = mContext.getResources();
        int dark = res.getColor(R.color.svgreen);


        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            button = new Button(mContext);
            button.setPadding(0, 0, 0, 0);
            button.setBackgroundResource(R.drawable.roundedbutton);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) 12.0);
            button.setTextColor(dark);
            button.setEnabled(true);
            button.setClickable(true);

        } else {
            button = (Button) convertView;
        }

        button.setText(sednica.getDatum().toString());
        button.setOnClickListener(new MyListener(position));

        return (View)button;
    }

    class MyListener implements View.OnClickListener{
        int pozicija;

        public MyListener(int pozicija) {
            this.pozicija = pozicija;
            Log.w(TAG," ---MyListener " + pozicija);
        }

        @Override
        public void onClick(View v) {
            Sednica sed = (Sednica) getItem(pozicija);
            AllStatic.currentSednica = sed;
            Log.w(TAG," *** clicked " + pozicija + " . " + sed.getDatum().toString());
            Intent drAkcija = new Intent(mContext, DnevniRedActivity.class);
            mContext.startActivity(drAkcija);
        }
    }
}
