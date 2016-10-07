package ru.anit.anitfresh.ui.page.catalogs;

import android.database.Cursor;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.databus.EventBusMessageOnChangedData;
import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 28.09.2016.
 */

public class PresenterPageCatalog implements IPresenterPageCatalog {

    CATALOGS catalog;
    IViewPageCatalog view;

    public PresenterPageCatalog(CATALOGS catalog, IViewPageCatalog view) {
        this.catalog = catalog;
        this.view = view;
    }


    private List<Catalog> getList() {

        Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_TYPE + "=?", new String[]{
                catalog.getType_entities().getNameFromBase()}, null);

        List<Catalog> list = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst())

        {
            do {

                ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);

                Catalog item = (Catalog) BuilderHelper.getObject(objectSyncTable);

                list.add(item);


            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }


        return list;

    }


    void redraw() {

        view.showProgress();
        Observable
                .create(subscriber -> {
                    subscriber.onNext(getList());
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(catalogs -> {
                    view.showList((List<Catalog>) catalogs);
                    view.hideProgress();
                });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessageOnChangedData event) {
        redraw();
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        view.showTvText(catalog.getName());
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
