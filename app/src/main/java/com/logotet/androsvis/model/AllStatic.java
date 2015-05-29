package com.logotet.androsvis.model;

import com.logotet.androsvis.model.Prilog;
import com.logotet.androsvis.model.Sednica;
import com.logotet.androsvis.model.Tacka;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by jankovicb on 5/12/15.
 */
public class AllStatic {
    public static String hostIP = null;
    public static int type = -1;
    public static int active = -1;
    public static final Semaphore LOCK = new Semaphore(0);
    public static List sednice = null;
    public static Sednica currentSednica;
    public static Tacka currentTacka;
    public static Prilog currentPrilog;
    public static int width;
    public static int height;

}
