package com.torv.adam.priapi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by AdamLi on 2016/12/2.
 */

public enum Instagram {
    instance;

    public LogedInUser mUser = new LogedInUser();

    public void login(final String username, final String passwd) {
        CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        cookieManager.getCookieStore().removeAll();

        final String url = Constants.API_URL + "accounts/login/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.d(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject userJson = jsonObject.optJSONObject("logged_in_user");
                    if(null != userJson) {
                        mUser.username = userJson.optString("username");
                        mUser.pk = userJson.optLong("pk");
                        getUserPost();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(error.getLocalizedMessage());
                try {
                    String str = new String(error.networkResponse.data, "GB2312");
                    L.e(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ig_sig_key_version", Constants.SIG_KEY_VERSION);
                JSONObject jsonObject = new JSONObject();
                String str = UUID.randomUUID().toString();
                try {
                    jsonObject.put("username", username);
                    jsonObject.put("password", passwd);
                    jsonObject.put("guid", str);
                    jsonObject.put("device_id", "android-" + str);

                    String jsonStr = jsonObject.toString();
                    String hash = SignatureUtils.hashMac(jsonStr, Constants.IG_SIG_KEY);
                    params.put("signed_body", hash + "." + jsonStr);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (SignatureException e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Util.addCommonHeaders(null);
            }
        };
        VolleyWrapper.instance.add(stringRequest);
    }

    public void getUserPost() {
        String url = Constants.API_URL + "feed/user/" + mUser.pk;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.d(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(error.getLocalizedMessage());
                try {
                    String str = new String(error.networkResponse.data, "GB2312");
                    L.e(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Util.addCommonHeaders(null);
            }
        };
        VolleyWrapper.instance.add(stringRequest);
    }

    public void logout() {
        String url = Constants.API_URL + "accounts/logout/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                L.d(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(error.getLocalizedMessage());
                try {
                    String str = new String(error.networkResponse.data, "GB2312");
                    L.e(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Util.addCommonHeaders(null);
            }
        };
        VolleyWrapper.instance.add(stringRequest);
    }
}
