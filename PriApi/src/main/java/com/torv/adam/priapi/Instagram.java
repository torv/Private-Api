package com.torv.adam.priapi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by AdamLi on 2016/11/29.
 */

public class Instagram {

    private String mUuid = "";
    private String mCookie = "";
    private String mToken = "";

    public void login(final String username, final String passwd) {

        mUuid = UUID.randomUUID().toString();

        // Challenge
        String challengeUrl = Constants.API_URL + "si/fetch_headers";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, challengeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.d(response);
                loginNextStep(username, passwd);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.d(error.getLocalizedMessage());
            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                L.d(response.toString());
                takeTokenFromHeaders(response.headers);
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("challenge_type", "signup");
                params.put("guid", UUID.randomUUID().toString().replaceAll("-", ""));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Util.addCommonHeaders(null);
            }
        };
        VolleyWrapper.instance.add(stringRequest);
    }

    private void takeTokenFromHeaders(Map<String, String> headers) {
        if(null != headers) {
            Set<String> keySet = headers.keySet();
            for(String key : keySet) {
                if("Set-Cookie".equals(key)) {
                    mCookie = headers.get(key);
                    mToken = mCookie.substring(10, mCookie.indexOf(";"));
                    L.d("mToken = " + mToken);
                }
            }
        }
    }

    private void loginNextStep(final String username, final String passwd) {
        String loninUrl = Constants.API_URL + "accounts/login/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loninUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.d(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.d(error.getLocalizedMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone_id", UUID.randomUUID().toString());
                params.put("_csrftoken", mToken);
                params.put("username", username);
                params.put("guid", mUuid);
                params.put("device_id", "android-"+UUID.randomUUID().toString().replaceAll("-", "").substring(0,16));
                params.put("password", passwd);
                params.put("login_attempt_count", "0");
                return SignatureUtils.generateSignature(params);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Util.addCommonHeaders(null);
            }
        };
        VolleyWrapper.instance.add(stringRequest);
    }
}
