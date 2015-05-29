package com.logotet.androsvis;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.model.Sednica;
import com.logotet.androsvis.threads.SedniceCatcher;


public class SedniceActivity extends ActionBarActivity {
    private static final String TAG = "SedniceActivity";
    ImageView naslov;
    Button sednica1Button;
    Button sednica0Button;
    Button arhivaButton;
    Sednica s1 = null;
    Sednica s0 = null;
    Intent drAkcija;
    Intent arhivaAkcija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sednice);

        naslov = (ImageView) findViewById(R.id.as_iv_headerImage);
        sednica1Button = (Button) findViewById(R.id.as_bt_sednica1);
        sednica0Button = (Button) findViewById(R.id.as_bt_sednica0);
        arhivaButton = (Button) findViewById(R.id.as_bt_arhiva);

        naslov.setImageResource((AllStatic.type == 0) ? R.drawable.svtopvece : R.drawable.svtskupstina);

        if(AllStatic.height < 500){
            LinearLayout.LayoutParams lp0 = (LinearLayout.LayoutParams) sednica0Button.getLayoutParams();
            LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) sednica1Button.getLayoutParams();
            lp0.topMargin = lp1.topMargin;
            LinearLayout.LayoutParams arp = (LinearLayout.LayoutParams) arhivaButton.getLayoutParams();
            arp.topMargin = lp1.topMargin;
//            sednica0Button.setHeight(12);
//            sednica1Button.setHeight(12);
//            arhivaButton.setHeight(12);
//            sednica0Button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//            sednica1Button.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
//            arhivaButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
//            Log.w(TAG,"modifikujem layout parametre");
        }


        SedniceCatcher sc = new SedniceCatcher();
        sc.start();
        try {
            AllStatic.LOCK.acquire();
        } catch (InterruptedException e) {
        }

        if(AllStatic.sednice.size() > 2)
            arhivaButton.setVisibility(View.VISIBLE);

        drAkcija = new Intent(this, DnevniRedActivity.class);
        try {
            s1 = (Sednica) AllStatic.sednice.get(1);
            sednica1Button.setText(s1.getDatum().toString());
            sednica1Button.setVisibility(View.VISIBLE);
        }catch (Exception npe){};
        try {
            s0= (Sednica) AllStatic.sednice.get(0);
            sednica0Button.setText(s0.getDatum().toString());
            sednica0Button.setVisibility(View.VISIBLE);
        }catch (Exception npe){};


        sednica1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllStatic.currentSednica = s1;
                startActivity(drAkcija);
            }
        });


        sednica0Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllStatic.currentSednica = s0;
                startActivity(drAkcija);
            }
        });


        arhivaAkcija = new Intent(this,ArhivaActivity.class);

        arhivaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(arhivaAkcija);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Log.w(TAG, "naslov width = " + naslov.getWidth());
//        Log.w(TAG, "naslov height = " + naslov.getHeight());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sednice, menu);
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
