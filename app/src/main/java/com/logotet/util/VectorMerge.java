package com.logotet.util;

import java.util.Enumeration;
import java.util.Vector;

/**
 * spajanje dva vectora u jedan
 */
public class VectorMerge {
    public static Vector merge(Vector v1, Vector v2) {
        Vector rez = new Vector(v1.size() + v2.size());
        Enumeration enm;
        enm = v1.elements();
        while (enm.hasMoreElements()) {
            Object obj = enm.nextElement();
            rez.add(obj);
        }

        enm = v2.elements();
        while (enm.hasMoreElements()) {
            Object obj = enm.nextElement();
            rez.add(obj);
        }
        return rez;
    }
}
