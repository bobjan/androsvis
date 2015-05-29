package com.logotet.androsvis.threads;

import android.util.Log;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.xmlparser.SednicaXMLHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class SedniceCatcher extends Thread {
    private static final String TAG = "SedniceCatcher";

    @Override
    public void run() {
        try {
            if(AllStatic.type == -1){
                Log.w(TAG,"type != -1");
                AllStatic.active = -1;
                AllStatic.sednice = null;
                AllStatic.LOCK.release();
                return;}
            String link = "http://" + AllStatic.hostIP + "/svisxml/sednice.php?tips=" + AllStatic.type;
//            Log.w(TAG,link);
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            Log.w(TAG,"Connection established");
            SednicaXMLHandler sxh = new SednicaXMLHandler(conn.getInputStream());
//            Log.w(TAG,"Handler created");
            sxh.doEntireJob();
//            Log.w(TAG,"XML parsed");
            AllStatic.sednice = sxh.getSednice();
//            Log.w(TAG,"static sednice " + AllStatic.sednice.size());

        } catch (IOException e) {
            Log.w(TAG,"IOException");
//
        }
        AllStatic.LOCK.release();


    }
}
