package com.logotet.androsvis;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.threads.HostSearch;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    Button skupstina;
    Button vece;
    Intent akcija;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMetrics();

       final Typeface face = Typeface.createFromAsset(getAssets(), "calibrib.ttf");
        skupstina = (Button)findViewById(R.id.am_bt_skupstina);
        vece = (Button)findViewById(R.id.am_bt_opvece);

        skupstina.setTypeface(face);
        vece.setTypeface(face);
//        tv.setTypeface(face);

        HostSearch hs = new HostSearch();
        hs.start();
        try {
            AllStatic.LOCK.acquire();
        } catch (InterruptedException e) {
        }

        if(AllStatic.hostIP == null) {
            AllStatic.active = -1;
            Toast.makeText(getApplicationContext(),
                    getApplicationContext().getString(R.string.network_error) ,
                    Toast.LENGTH_LONG).show();

        }else{
            skupstina.setVisibility(View.VISIBLE);
            vece.setVisibility(View.VISIBLE);
        }

       	akcija = new Intent(this,SedniceActivity.class);
        skupstina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllStatic.type = 1;
            	startActivity(akcija);
            }
        });

        vece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllStatic.type = 0;
                startActivity(akcija);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void testMetrics(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double width=(double)dm.widthPixels;
        double height=(double)dm.heightPixels;
        double dens=(double)dm.densityDpi;
        AllStatic.width = (int)(width * 160.0/dens);
        AllStatic.height = (int)(height * 160.0/dens);
//        Log.w(TAG,"width = " + AllStatic.width + "dp");
//        Log.w(TAG,"height = " + AllStatic.height + "dp" );
//        Log.w(TAG,"density = " + dens );
    }
}
