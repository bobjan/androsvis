package com.logotet.androsvis.model;

import com.logotet.androsvis.model.Prilog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tacka {
    private List prilozi;
    private int rednibroj;
    private int podbroj;
    private String naziv;
    private String izvestilac;
    private String godina;

    public Tacka() {
        prilozi = new ArrayList();
        naziv = "";
        izvestilac = "";
    }

    public int getRednibroj() {
        return rednibroj;
    }

    public void setRednibroj(int rb) {
        this.rednibroj = rb;
    }

    public int getPodbroj() {
        return podbroj;
    }

    public void setPodbroj(int pbr) {
        this.podbroj = pbr;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = (naziv != null) ? naziv : "";
    }

    public String getIzvestilac() {
        return izvestilac;
    }

    public void setIzvestilac(String izvestilac) {
        this.izvestilac = (izvestilac != null) ? izvestilac : "";
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
    }

    public boolean isPodtacka() {
        return (podbroj > 0);
    }
    public void addPrilog(Prilog p){
        prilozi.add(p);
    }

    public List getPrilozi() {
        return prilozi;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator enm = prilozi.iterator();
        while(enm.hasNext()){
            Prilog p = (Prilog) enm.next();
            sb.append("\n\t\t" + p.toString());
        }
        return "Tacka :" + rednibroj + "-" + podbroj + "\t" + naziv + sb.toString() + "\nTHE END\n";
    }

}