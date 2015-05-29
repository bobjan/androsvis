package com.logotet.util;

/**
 * klasa koju treba da naslede pravi kljucevi
 */
public abstract class AbstractKey {
    protected String code;

    public String getCode() {
        return code;
    };
    public String toString() {
        return code;
    };
}
