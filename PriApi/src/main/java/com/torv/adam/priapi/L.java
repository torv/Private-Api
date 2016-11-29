package com.torv.adam.priapi;

import android.util.Log;

/**
 * Created by AdamLi on 2015/12/2.
 */
public class L {

    public static final String DEFAULT_TAG = "Cosmos";

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stack[] = Thread.currentThread().getStackTrace();
            msg = stack[3].getClassName()+"."+stack[3].getMethodName() +"()<"+stack[3].getLineNumber() + "> : "+ msg;
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stack[] = Thread.currentThread().getStackTrace();
            msg = stack[3].getClassName()+"."+stack[3].getMethodName() +"()<"+stack[3].getLineNumber() + "> : "+ msg;
            Log.e(DEFAULT_TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stack[] = Thread.currentThread().getStackTrace();
            msg = stack[3].getClassName()+"."+stack[3].getMethodName() +"()<"+stack[3].getLineNumber() + "> : "+ msg;
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stack[] = Thread.currentThread().getStackTrace();
            msg = stack[3].getClassName()+"."+stack[3].getMethodName() +"()<"+stack[3].getLineNumber() + "> : "+ msg;
            Log.d(DEFAULT_TAG, msg);
        }
    }
}
