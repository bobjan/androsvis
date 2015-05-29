package com.logotet.androsvis;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.logotet.androsvis.model.AllStatic;


public class PrilogActivity extends ActionBarActivity {
    private static final String TAG = "PrilogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prilog);

        WebView wv = (WebView) findViewById(R.id.ap_wv_webdokument);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        String src="http://docs.google.com/gview?embedded=true&url=http://" + AllStatic.currentPrilog.getHref() ;
        String iframe="<iframe src='" + src +  "' width='100%' height='100%' style='border: none;'></iframe>";
//        Log.w(TAG, iframe);
        wv.loadData(iframe, "text/html", "UTF-8");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prilog, menu);
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
