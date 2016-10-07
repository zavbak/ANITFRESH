package ru.anit.anitfresh.ui.page.tasks.lists;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;


import ru.anit.anitfresh.databus.EventBusMessageOnChangedData;
import ru.anit.anitfresh.databus.EventBusMessageOnError;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 28.09.2016.
 */

public class PresenterFragmentTasksList implements IPresenterFragmentTaskList {

    TASKS tasks;
    IViewFragmentTaskList view;

    public PresenterFragmentTasksList(TASKS tasks, IViewFragmentTaskList view) {

        this.tasks = tasks;
        this.view = view;
    }

    void redraw() {

        view.showProgress();
        Observable
                .create(subscriber -> {


                    try {
                        subscriber.onNext(new CashList(tasks));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        subscriber.onError(new Throwable("Ошибка заполнения списка!"));
                    }

                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cashList -> {

                    view.showList((CashList) cashList);
                    view.hideProgress();
                }, throwable -> {

                    view.hideProgress();
                    EventBusMessageOnError.sendMessage(throwable.getMessage());

                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessageOnChangedData event) {
        redraw();
    }


    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        view.showTvText(tasks.getName());
        redraw();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadState(Bundle savedInstanceState) {

    }

    @Override
    public void saveState(Bundle savedInstanceState) {

    }
}
