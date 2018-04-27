package org.bcbs.gateway.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

@Component
public class CryptoHelper {

    private static final String XOR_CRYPTO_KEY = "org.bcbs.crypto";

    private CryptoHelper() {
    }

    public static String md5Encode(String data) {
        return (data == null || data.isEmpty()) ? null : byteToHex(digest("MD5", data.getBytes()));
    }

    public static String doubleMd5Encode(String data) {
        return md5Encode(md5Encode(data));
    }

    private static String byteToHex(byte[] data) {
        String hex = null;

        if (data != null && data.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (byte b : data)
                sb.append(String.format("%02x", b & 0xFF));

            hex = sb.toString();
        }
        return hex;
    }

    private static byte[] digest(String algorithm, byte[] data) {
        return digest(algorithm, data, 0, data.length);
    }

    private static byte[] digest(String algorithm, byte[] data, int offset, int length) {
        byte[] digest = null;

        if (data != null && data.length > 0 && data.length >= length + offset) {
            try {
                MessageDigest md = MessageDigest.getInstance(algorithm);
                md.update(data, offset, length);
                digest = md.digest();
            } catch (Exception ex) {
                // Just ignore the exception & return null.
                digest = null;
            }
        }
        return digest;
    }

    public static String generateComplexPassword(int length) {
        return randomPassword(length, new char[][]{{'0', '9'}, {'a', 'z'}, {'A', 'Z'}});
    }

    private static String randomPassword(int length, char[][] dataPool) {
        String value = null;
        Random random = new Random();

        if (length > 0 && dataPool != null && dataPool.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char[] temp = dataPool[random.nextInt(dataPool.length)];
                sb.append((char) (temp[0] + random.nextInt(temp[1] - temp[0] + 1)));
            }
            value = sb.toString();
        }
        return value;
    }

    public static byte[] xorEncrypt(byte[] plainData) {
        if (plainData == null)
            plainData = new byte[0];

        if (plainData.length > 0) {
            byte[] byteKey = XOR_CRYPTO_KEY.getBytes();
            for (int i = 0; i < plainData.length; i++)
                plainData[i] ^= byteKey[i % byteKey.length];

            plainData = Base64.getEncoder().encode(plainData);
        }
        return plainData;
    }

    public static byte[] xorDecrypt(byte[] cipherData) {
        if (cipherData == null)
            cipherData = new byte[0];

        if (cipherData.length > 0) {
            cipherData = Base64.getDecoder().decode(cipherData);

            byte[] byteKey = XOR_CRYPTO_KEY.getBytes();
            for (int i = 0; i < cipherData.length; i++)
                cipherData[i] ^= byteKey[i % byteKey.length];
        }
        return cipherData;
    }
}
