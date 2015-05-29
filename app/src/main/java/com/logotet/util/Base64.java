package com.logotet.util;

/**
 * ***********************************************************************
 * <p/>
 * A Base64 Encoder/Decoder.
 * <p/>
 * Jos nije do kraja istestirano. Za sada radi samo sa stringovima, a kasnije ce biti prosira i na fajlove, tj. strimove
 * (jer nije hendlovano ogranicenje na max. 76 karaktera u liniji ...)
 * <p/>
 * <p/>
 *
 * @author Boban Jankovic
 * @version 1.0
 * @see
 */

public class Base64 {

// Mapping table from 6-bit nibbles to Base64 characters.
    private static char[] tabela64 = new char[64];// 64 karaktera za kodiranje
    private static byte[] mapa = new byte[128];// za dekodiranje

    public Base64() {
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++)
            tabela64[i++] = c;
        for (char c = 'a'; c <= 'z'; c++)
            tabela64[i++] = c;
        for (char c = '0'; c <= '9'; c++)
            tabela64[i++] = c;
        tabela64[i++] = '+';
        tabela64[i++] = '/';

        for (i = 0; i < mapa.length; i++)
            mapa[i] = -1;

        for (i = 0; i < 64; i++)
            mapa[tabela64[i]] = (byte) i;

    }


    /**
     * Kodira a string u  Base64 format.
     *
     * @param s a String to be encoded.
     * @return	A String with the Base64 encoded data.
     */
    public String encode(String s) {
        return new String(encode(s.getBytes()));
    }

    /**
     * Kodira byte niz u Base64 format.
     *
     * @param ulaz byte array to be encoded.
     * @return	A Base64 encoded character array.
     */
    public char[] encode(byte[] ulaz) {
        int inputSize = ulaz.length;
        int oDataLen = (inputSize * 4 + 2) / 3;	// output length without padding
        int oLen = ((inputSize + 2) / 3) * 4;	 // output length including padding
        char[] izlaz64 = new char[oLen];
        int inputPointer = 0;
        int outputPointer = 0;
        while (inputPointer < inputSize) {
            int byte0 = ulaz[inputPointer++] & 0xff;
            int byte1 = inputPointer < inputSize ? ulaz[inputPointer++] & 0xff : 0;
            int byte2 = inputPointer < inputSize ? ulaz[inputPointer++] & 0xff : 0;

            int char0 = byte0 >>> 2;
            int char1 = ((byte0 & 3) << 4) | (byte1 >>> 4);
            int char2 = ((byte1 & 0xf) << 2) | (byte2 >>> 6);
            int char3 = byte2 & 0x3F;


            izlaz64[outputPointer++] = tabela64[char0];
            izlaz64[outputPointer++] = tabela64[char1];
            izlaz64[outputPointer] = outputPointer < oDataLen ? tabela64[char2] : '=';
            outputPointer++;
            izlaz64[outputPointer] = outputPointer < oDataLen ? tabela64[char3] : '=';
            outputPointer++;
        }
        return izlaz64;
    }

    /**
     * Decodes a Base64 string.
     *
     * @param s a Base64 String to be decoded.
     * @return	A String containing the decoded data.
     * @throws	IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public String decode(String s) {
        return new String(decode(s.toCharArray()));
    }

    /**
     * Dekodira Base64 podatke.
     * ignores all characters which are not Base64 compliant.
     * If input array length is unproper, ignores it.???
     *
     * @param ulaz64 a Base64 encoded  character array.
     * @return	An array containing the decoded data bytes.
     * @throws	IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public byte[] decode(char[] ulaz64) {
        int inputSize = ulaz64.length;
        if (inputSize % 4 != 0) throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        while (inputSize > 0 && ulaz64[inputSize - 1] == '=')
            inputSize--;

        int oLen = (inputSize * 3) / 4;
        byte[] izlaz = new byte[oLen];
        int inputPointer = 0;
        int outputPointer = 0;

        while (inputPointer < inputSize) {
            int i0 = ulaz64[inputPointer++];
            int i1 = ulaz64[inputPointer++];
            int i2 = inputPointer < inputSize ? ulaz64[inputPointer++] : 'A';
            int i3 = inputPointer < inputSize ? ulaz64[inputPointer++] : 'A';

            if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            int b0 = mapa[i0];
            int b1 = mapa[i1];
            int b2 = mapa[i2];
            int b3 = mapa[i3];
            if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            int o0 = (b0 << 2) | (b1 >>> 4);
            int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
            int o2 = ((b2 & 3) << 6) | b3;
            izlaz[outputPointer++] = (byte) o0;
            if (outputPointer < oLen)
                izlaz[outputPointer++] = (byte) o1;
            if (outputPointer < oLen)
                izlaz[outputPointer++] = (byte) o2;
        }
        return izlaz;
    }
	
	
	
// LayeredTest program for Base64Coder class.


// LayeredTest Base64Coder with constant strings.
    private static void test1() {
        System.out.println("test1 started");
        Base64 bc = new Base64();
        String s;
        s = bc.encode("Hello");
        s = bc.decode(s);
        if (!s.equals("Hello")) System.out.println("Error 1");
        //
        final String c1 = "QWxhZGRpbjpvcGVuIHNlc2FtZQ==";   // example from RFC 2617
        s = bc.decode(c1);
        if (!s.equals("Aladdin:open sesame")) System.out.println("Error 2");
        s = bc.encode(s);
        if (!s.equals(c1)) System.out.println("Error 3");
        System.out.println("test1 completed");
    }

// Compares two byte arrays.
    private static boolean compareByteArrays(byte[] a1, byte[] a2) {
        if (a1.length != a2.length) return false;
        for (int p = 0; p < a1.length; p++)
            if (a1[p] != a2[p]) return false;
        return true;
    }

// LayeredTest Base64Coder against sun.misc.BASE64Encoder/Decoder with
// random strings.
//    private static void test2() throws Exception {
//        System.out.println("test2 started");
//        Base64 bc = new Base64();
//        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
//        java.util.Random rnd = new java.util.Random(0x538afb92);
//        for (int i = 0; i < 10000; i++) {
//            int len = rnd.nextInt(55);
//            byte[] b0 = new byte[len];
//            rnd.nextBytes(b0);
//            char[] e1 = bc.encode(b0);
//            String es1 = new String(e1);
//            String e2 = enc.encode(b0);
//            if (!es1.equals(e2)) System.out.println("Error\ne1=" + es1 + " len=" + es1.length() + "\ne2=" + e2 + " len=" + e2.length());
//            byte[] b1 = bc.decode(e1);
//            byte[] b2 = dec.decodeBuffer(e2);
//            if (!compareByteArrays(b1, b0) || !compareByteArrays(b2, b0))
//                System.out.println("Decoded data not equal. len1=" + b1.length + " len2=" + b2.length);
//        }
//        System.out.println("test2 completed");
//    }

//    public static void main(String args[]) throws Exception {
//        System.out.println("TestBase64 started");
//        test1();
//        test2();
//        System.out.println("TestBase64 completed");
//    }
}