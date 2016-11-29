package com.torv.adam.priapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by AdamLi on 2016/11/29.
 */

public class SignatureUtils {

    private static final String HASH_ALGORITHM = "HmacSHA256";

    public static Map<String, String> generateSignature(Map<String, String> postParams) {
        if (null != postParams) {
            // Map -> Json String
            JSONObject jsonObject = new JSONObject();
            Set<String> keySet = postParams.keySet();
            try {
                for (String key : keySet) {
                    jsonObject.put(key, postParams.get(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsonStr = jsonObject.toString();
            String hash = hash_hmac_256(jsonStr, Constants.IG_SIG_KEY);

            // Signed params
            Map<String, String> resultParams = new HashMap<>();
            resultParams.put("ig_sig_key_version", Constants.SIG_KEY_VERSION);
            String encodedStr = "";
            try {
                encodedStr = URLEncoder.encode(jsonStr, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            resultParams.put("signed_body", hash + "." + encodedStr);
            return resultParams;
        }
        return new HashMap<>();
    }

    private static String hash_hmac_256(String str, String key) {
        String hash = "";
        try {
            hash = hashMac(str, key);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * php version.
     */
    @Deprecated
    public static String generateSignature(String jsonData) {
        String result = "";
        String hash = "";
        try {
            hash = hashMac(jsonData, Constants.IG_SIG_KEY);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        try {
            result = "ig_sig_key_version=" + Constants.SIG_KEY_VERSION + "&signed_body=" + hash + "." + URLEncoder.encode(jsonData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Encryption of a given text using the provided secretKey
     *
     * @param text
     * @param secretKey
     * @return the encoded string
     * @throws SignatureException
     */
    public static String hashMac(String text, String secretKey)
            throws SignatureException {

        try {
            Key sk = new SecretKeySpec(secretKey.getBytes(), HASH_ALGORITHM);
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            mac.init(sk);
            final byte[] hmac = mac.doFinal(text.getBytes());
            return toHexString(hmac);
        } catch (NoSuchAlgorithmException e1) {
            throw new SignatureException("error building signature, no such algorithm in device " + HASH_ALGORITHM);
        } catch (InvalidKeyException e) {
            throw new SignatureException("error building signature, invalid key " + HASH_ALGORITHM);
        }
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return sb.toString();
    }

}
