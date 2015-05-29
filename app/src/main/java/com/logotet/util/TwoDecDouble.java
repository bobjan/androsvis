package com.logotet.util;

/**
 * jednostavna klasa koja ima svrhu samo da uvek za double vraca broj sa dva decimala
 */
public class TwoDecDouble {
    private double value;

    public TwoDecDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString() {
        if (value == 0.0)
            return " ";
        return MainConverter.doubleToString(value, 2);
    }
}
