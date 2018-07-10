package org.bcbs.systemcore.lib.auth.common.utility;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

public abstract class CryptoHelper {

    private CryptoHelper() {
    }

    public static String md5Encode(String data) {
        return StringUtils.isEmpty(data) ? null : new String(Hex.encode(digest("MD5", data.getBytes())));
    }

    public static String doubleMd5Encode(String data) {
        return md5Encode(md5Encode(data));
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

    public static String generateTokenKey() {
        return UUID.randomUUID().toString();
    }
}
