package com.torv.adam.priapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AdamLi on 2016/11/29.
 */

public class Util {

    public static Map<String, String> addCommonHeaders(Map<String, String> headers) {
        if(null == headers) {
            headers = new HashMap<>();
        }

//        headers.put("Connection", "close");
//        headers.put("Accept", "*/*");
//        headers.put("X-IG-Capabilities", Constants.X_IG_Capabilities);
//        headers.put("X-IG-Connection-Type", "WIFI");
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        headers.put("Accept-Language", "en-US");
        String userAgent = "Instagram " + Constants.VERSION +" Android (23/6.0.1; 640dpi; 1440x2560; samsung; SM-G9200; zerofltechn; samsungexynos7420; zh_CN)";
        headers.put("User-Agent", userAgent);

        return headers;
    }
}
