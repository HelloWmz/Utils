package com.wmz.utils.utils;

import android.util.Log;

/**
 */


public class LogUtil {

    private static boolean bossLog = true;

    public static void d(String TAG, String msg, boolean isLog) {
        if (bossLog && isLog)
            Log.d(TAG, msg);
    }

    public static void e(String TAG, String msg, boolean isLog) {
        if (bossLog && isLog)
            Log.e(TAG, msg);
    }

}
