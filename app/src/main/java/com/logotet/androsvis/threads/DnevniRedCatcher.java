package com.logotet.androsvis.threads;

import android.util.Log;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.xmlparser.DnevniRedXMLHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class DnevniRedCatcher extends Thread {
    private static final String TAG = "DnevniRedCatcher";

    @Override
    public void run() {
        try {

            String link = "http://" + AllStatic.hostIP + "/svisxml/dnevnired.php?sid=" + AllStatic.currentSednica.getSednicaid();
//            Log.w(TAG,link);
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            DnevniRedXMLHandler sxh = new DnevniRedXMLHandler(conn.getInputStream());
            sxh.doEntireJob();
            AllStatic.currentSednica = sxh.getCurrentSednica();
//            Log.w(TAG,AllStatic.currentSednica.getFull());
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
        }
        AllStatic.LOCK.release();
    }
}
