package ru.anit.anitfresh.data.services.exchangeksoap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.anit.anitfresh.Interators.synchronizer.helpers.SynchronizerHelper;
import ru.anit.anitfresh.databus.EventBusMessageOnInform;


/**
 * Сервис обменивается с 1С в интефэйсе processingRecuest все действия
 */
public class ServisExchangeKsoap extends Service {


    public final static String START_WITH_NOTIFI = "strat_witn_notifi";

    ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Boolean notifi = intent.getBooleanExtra(START_WITH_NOTIFI, false);

        try {

            MyRun mr = new MyRun(notifi);
            executorService.execute(mr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyRun implements Runnable {

        boolean notifi;

        public MyRun(boolean notifi) {
            this.notifi = notifi;
        }

        @Override
        public void run() {

            EventBusMessageOnInform.sendMessage("Start Servis");
            try {
                SynchronizerHelper.start(notifi);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
