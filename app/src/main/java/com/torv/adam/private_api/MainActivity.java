package com.torv.adam.private_api;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.torv.adam.priapi.Instagram;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go(View view) {
        Instagram.instance.login("username", "password");
    }

    public void logout(View view) {
        Instagram.instance.logout();
    }
}
