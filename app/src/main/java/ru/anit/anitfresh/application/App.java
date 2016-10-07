package ru.anit.anitfresh.application;

import android.app.Application;
import android.content.Context;


/**
 * Created by Александр on 07.09.2016.
 */
public class App extends Application {

    private static App sInstance;


    public static synchronized App get() {
        return sInstance;
    }


    public static Context getContext() {

        return App.get().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();

    }

    private void initializeInstance() {

    }


    @Override
    public void onTerminate() {
        // Do your application wise Termination task
        super.onTerminate();
    }
}
