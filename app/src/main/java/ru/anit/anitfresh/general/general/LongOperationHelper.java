package ru.anit.anitfresh.general.general;

import java.util.concurrent.TimeUnit;

/**
 * Created by Александр on 28.09.2016.
 */

public class LongOperationHelper {

    public static String LongOperation(int sec) {

        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Long task: " + sec + " ";
    }

}
