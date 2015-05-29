package com.logotet.androsvis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.model.Prilog;
import com.logotet.androsvis.model.Tacka;


public class TackaActivity extends ActionBarActivity {
    private static final String TAG = "TackaActivity";
    private TextView tvBrojSednice;
    private TextView tvDatumSednice;
    private TextView tvBrojTacke;
    private TextView tvNaziv;
    private TextView tvIzvestilac;

    private ListView listView;
    private TackaPrilogAdapter adapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_tacka);

        tvBrojSednice = (TextView) findViewById(R.id.at_tv_brojSednice);
        tvDatumSednice = (TextView) findViewById(R.id.at_tv_datumSednice);
        tvBrojTacke = (TextView) findViewById(R.id.at_tv_brojTacke);
        tvNaziv = (TextView) findViewById(R.id.at_tv_naslovTacke);
        tvIzvestilac = (TextView) findViewById(R.id.at_tv_izvestilac);
//        Log.w(TAG, "pocelo");

        Tacka tacka = AllStatic.currentTacka;
        tvBrojSednice.setText(AllStatic.currentSednica.getBroj()  + ".");
        tvDatumSednice.setText(AllStatic.currentSednica.getDatum().toString());
        tvBrojTacke.setText(tacka.getRednibroj() + ((tacka.getPodbroj() == 0) ? "." : "*."));
        tvNaziv.setText(tacka.getNaziv());
        tvIzvestilac.setText(tacka.getIzvestilac());

        listView = (ListView) findViewById(R.id.at_lv_listaPriloga);
        adapter = new TackaPrilogAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prilog prilog = (Prilog) adapter.getItem(position);
                AllStatic.currentPrilog = prilog;
                Intent intent = new Intent(context, PrilogActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tacka, menu);
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
