package org.flyve.inventory;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptoUtil {

    private final static String CRYPTO_METHOD = "RSA";
    private final static int CRYPTO_BITS = 2048;

    public static Map<String, String> generateKeyPair()
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(CRYPTO_METHOD);
        kpg.initialize(CRYPTO_BITS);
        KeyPair kp = kpg.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        Map<String, String> map = new HashMap<>();
        map.put("privateKey", Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT));
        map.put("publicKey", Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT));
        return map;
    }

    public static String encrypt(String plain, String pubk)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            InvalidKeySpecException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, stringToPublicKey(pubk));
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    public static String decrypt(String result, String privk)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeySpecException,
            InvalidKeyException {

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, stringToPrivateKey(privk));
        byte[] decryptedBytes = cipher.doFinal(Base64.decode(result, Base64.DEFAULT));
        return new String(decryptedBytes);
    }

    private static PublicKey stringToPublicKey(String publicKeyString)
            throws InvalidKeySpecException,
            NoSuchAlgorithmException {

            byte[] keyBytes = Base64.decode(publicKeyString, Base64.DEFAULT);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(CRYPTO_METHOD);
            return keyFactory.generatePublic(spec);
    }

    private static PrivateKey stringToPrivateKey(String privateKeyString)
            throws InvalidKeySpecException,
            NoSuchAlgorithmException {

        byte [] pkcs8EncodedBytes = Base64.decode(privateKeyString, Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance(CRYPTO_METHOD);
        return kf.generatePrivate(keySpec);
    }
}
