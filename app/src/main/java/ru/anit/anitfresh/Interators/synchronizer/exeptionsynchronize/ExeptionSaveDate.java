package ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import ru.anit.anitfresh.general.general.LogHelper;

/**
 * Created by 79900 on 17.09.2016.
 */
public class ExeptionSaveDate extends Exception {


    public ExeptionSaveDate(String message) {
        super(message);
        FirebaseCrash.report(new Exception("ANIT ExeptionSaveDate :" + message));

    }
}
