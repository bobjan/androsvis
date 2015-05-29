package com.logotet.androsvis.model;

import android.util.Log;

import com.logotet.androsvis.R;

import java.util.Hashtable;

public class Prilog {
    private static final String TAG = " Prilog ";
    private String opis;
    private String naziv;
    private String velicina;

    private String href;

    public Prilog() {

    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getVelicina() {
        return velicina;
    }

    public void setVelicina(String velicina) {
        this.velicina = velicina;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = AllStatic.hostIP + href;
    }

    public String toString() {
        return "P-opis:" + opis + "\n\tP-naziv:" + naziv + "\n\tP-href:" + href + "\t" + velicina;
    }

    public int getResourceId(){
        String tip = "";
        try  {
        tip = href.substring(href.lastIndexOf(".") + 1).toLowerCase();
        if (tip.length() > 3)
            tip = tip.substring(0, 3);
//            Log.w(TAG,href);
//            Log.w(TAG,tip);
    }catch (Exception e){}


        switch(tip){
            case "doc":
                return R.drawable.doc;
            case "ppt":
                return R.drawable.ppt;
            case "xls":
                return R.drawable.xls;
            case "pdf":
                return R.drawable.pdf;
            case "png":
            case "tif":
            case "jpg":
                return R.drawable.img;
            default:
                return R.drawable.rtf;
        }
    }

}