package com.torv.adam.private_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.torv.adam.priapi.Instagram;
import com.torv.adam.priapi.SignatureUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go(View view) {
        new Instagram().login("username", "password");
    }
}
