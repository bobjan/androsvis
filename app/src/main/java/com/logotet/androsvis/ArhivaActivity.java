package com.logotet.androsvis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.model.Sednica;


public class ArhivaActivity extends ActionBarActivity {
    private static final String TAG = "ArhivaActivity";
    ImageView naslov;
    ArhivaAdapter adapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arhiva);
        context = getApplicationContext();


        naslov = (ImageView) findViewById(R.id.aa_iv_headerImage);
        naslov.setImageResource((AllStatic.type == 0) ? R.drawable.svtopvece : R.drawable.svtskupstina);

        GridView gridview = (GridView) findViewById(R.id.aa_gv_arhiva);
        adapter = new ArhivaAdapter(this);
        gridview.setAdapter(adapter);

/*
        Log.w(TAG, "***  dovde je dobro ***");
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w(TAG,"onItemClickListener enetered " + position);
                Sednica sed = (Sednica) adapter.getItem(position);
                AllStatic.currentSednica = sed;
                Intent drAkcija = new Intent(context, DnevniRedActivity.class);
                startActivity(drAkcija);
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arhiva, menu);
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
