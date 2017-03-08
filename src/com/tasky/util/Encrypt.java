package com.tasky.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by markus on 2017-03-09.
 */
public class Encrypt {

    /**
     * Encrypt a string using MD5 encryption
     * @param toEncrypt The string to be encrypted
     * @return  Encrypted string
     */
    public static String encrypt(String toEncrypt) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(toEncrypt.getBytes());

            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String encrypted = bigInt.toString(16);

            while(encrypted.length() < 32) {
                encrypted = "0" + encrypted;
            }

            return encrypted;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 Algorithm not available " + e);
        }
    }
}
