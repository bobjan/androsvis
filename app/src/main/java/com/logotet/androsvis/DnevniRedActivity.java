package com.logotet.androsvis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.model.Tacka;
import com.logotet.androsvis.threads.DnevniRedCatcher;

import pl.polidea.view.ZoomView;


public class DnevniRedActivity extends ActionBarActivity {
    private static final String TAG = "DnevniRedActivity";
    TextView tvNaslov;
    TextView tvBroj;
    TextView tvDatum;

    private ListView listView;
    private DnRedAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    context = getApplicationContext();
        // window features - must be set prior to calling setContentView... ???????
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_dnevni_red);
//        Log.w(TAG, "poceo dnevni red");

        tvNaslov = (TextView) findViewById(R.id.adr_tv_naslovSednice);
        tvBroj = (TextView) findViewById(R.id.adr_tv_brojSednice);
        tvDatum = (TextView) findViewById(R.id.adr_tv_datumSednice);

//        Log.w(TAG,"spremam se da pozovem thread");
        DnevniRedCatcher dc = new DnevniRedCatcher();
        dc.start();
        try {
            AllStatic.LOCK.acquire();
        } catch (InterruptedException e) {
        }

//        Log.w(TAG, "LOCK acquired");
        tvNaslov.setText((AllStatic.type == 0) ? getApplicationContext().getString(R.string.button0) : getApplicationContext().getString(R.string.button1)) ;
        tvBroj.setText(AllStatic.currentSednica.getBroj() + "");
        tvDatum.setText(AllStatic.currentSednica.getDatum().toString());

        listView = (ListView) findViewById(R.id.adr_lv_listaTacaka);
        adapter = new DnRedAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.w(TAG, "clicked with long on " + position);
                Tacka tacka = (Tacka) adapter.getItem(position);
                AllStatic.currentTacka = tacka;
                Intent intent = new Intent(context, TackaActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dnevni_red, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
