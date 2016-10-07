package ru.anit.anitfresh.general.general;

import android.util.Log;

/**
 * Created by Александр on 23.09.2016.
 */

public class LogHelper {

    private static String LOG_TAG = "anit";

    public static void d(String message){
        Log.d(LOG_TAG,message);
    }

    public static void e(String message){
        Log.e(LOG_TAG,message);
    }

}
