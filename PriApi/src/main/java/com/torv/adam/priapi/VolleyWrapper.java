package com.torv.adam.priapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by AdamLi on 2016/11/29.
 */

public enum  VolleyWrapper {
    instance;

    RequestQueue mRequestQueue;
    public void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void add(Request request) {
        if(null != mRequestQueue) {
            mRequestQueue.add(request);
        }
    }
}
