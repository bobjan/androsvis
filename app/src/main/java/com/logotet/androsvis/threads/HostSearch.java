package com.logotet.androsvis.threads;

import com.logotet.androsvis.model.AllStatic;
import com.logotet.androsvis.HttpCatcher;
import com.logotet.androsvis.xmlparser.HostIPHandler;

/**
 * Created by boban on 5/20/15.
 */
public class HostSearch extends Thread {
    private static final String TAG = "HostSearch";

    @Override
    public void run() {
        AllStatic.hostIP = null;
        String svlink = "http://savskivenac.rs/svishostip.php";
        HttpCatcher http = new HttpCatcher(svlink);
        HostIPHandler hip = new HostIPHandler(http.getInputStream());
        hip.doEntireJob();
        if(hip.isOk()) {
            AllStatic.hostIP = hip.getHostIp();
        }

        AllStatic.LOCK.release();

    }
}
