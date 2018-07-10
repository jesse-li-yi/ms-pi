package org.bcbs.gateway.common.utility;

import java.util.Base64;

public abstract class XorCryptoHelper {

    private static final String XOR_CRYPTO_KEY = "org.bcbs.crypto";

    private XorCryptoHelper() {
    }

    public static byte[] encrypt(byte[] plainData) {
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

    public static byte[] decrypt(byte[] cipherData) {
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
