package com.logotet.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Static metode za hashiranje String passord argumenta u jednom pravcu. <br />
 * Koristi se MD5 algoritam za digitalno potpisivanje, a potom se Base64 encodira.
 * Tako je dobijen String od 24 karaktera, a kao takav se moze smestiti u bazu i koristiti za
 * kasnija poredjenja.
 * <BR>
 *
 * @author Boban Jankovic
 * @version 1.0
 * @see
 */
public class PasswordHasher {
    private String hashedValue;

    public PasswordHasher(String ulaz) {
        hashedValue = encrypt(ulaz);
    }

    /**
     * Vraca 24 karaktera dugacak String
     */
    public static String getHashedValue(String ulaz) {
        return (new PasswordHasher(ulaz)).getHashedValue();
    }

    public String getHashedValue() {
        return hashedValue;
    }

    private synchronized String encrypt(String plaintext) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm " + e.getMessage());
        }


        try {
            md.update(plaintext.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("No such encoding " + e.getMessage());
        }

        byte raw[] = md.digest(); //step 4
        Base64 bc = new Base64();
        String hash = new String(bc.encode(raw));

        return hash; //step 6
    }

    public static void main(String[] args) {
        PasswordHasher owh = new PasswordHasher(args[0]);
        System.out.println(args[0] + " " + owh.getHashedValue());
//		System.out.println("or " + (int)forOr);
    }

}