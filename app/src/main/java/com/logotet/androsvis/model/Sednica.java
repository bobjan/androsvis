package com.logotet.androsvis.model;


import com.logotet.util.BJDatum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Sednica {
    private List dnevnired = new ArrayList();

    private int sednicaid;		// AUTOINCREMENT
    private int tip;
    private int broj;
    private BJDatum datum;


    public Sednica(String id, String br, String  dat) {
        this();
        try{
        this.sednicaid = Integer.parseInt(id.trim());
        this.broj = Integer.parseInt(br.trim());
        }catch(NumberFormatException nfe){
            sednicaid = 0;
            broj = 0;
        }
        try {
            this.datum = new BJDatum(dat);
        } catch (ParseException e) {
            this.datum = null;
        }
    }

    public Sednica() {
        dnevnired = new Vector();
    }

    public int getSednicaid() {
        return sednicaid;
    }

    public void setSednicaid(int sednicaid) {
        this.sednicaid = sednicaid;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public void setDatum(BJDatum datum) {
        this.datum = datum;
    }

    public void setDatum(String datum) {
        try {
            setDatum(new BJDatum(datum));
        } catch (ParseException e) {
            setDatum(new BJDatum());
        }
    }

    public List getDnevnired() {
        return dnevnired;
    }

    public boolean isOK(){
        return (sednicaid != 0) && (broj != 0) && (datum != null);
    }

    public String toString() {
        String tmp = (datum != null) ? datum.toString() : "null";
        return broj + "\t" + sednicaid + "\t" + tmp;
    }

    public void addTacka(Tacka t){
        dnevnired.add(t);
    }
    public String getFull(){
        StringBuffer sb = new StringBuffer();
        Iterator enm = dnevnired.iterator();
        while(enm.hasNext()){
            Tacka t = (Tacka) enm.next();
            sb.append(t.toString());
        }
        return toString() + "\n" + sb.toString();
    }
}