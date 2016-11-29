package com.torv.adam.private_api;

import android.app.Application;

import com.torv.adam.priapi.VolleyWrapper;

/**
 * Created by AdamLi on 2016/11/29.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyWrapper.instance.init(this);
    }
}
