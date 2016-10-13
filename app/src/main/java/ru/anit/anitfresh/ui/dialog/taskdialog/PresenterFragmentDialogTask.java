package ru.anit.anitfresh.ui.dialog.taskdialog;

import android.database.Cursor;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.anit.anitfresh.Interators.IInterator;
import ru.anit.anitfresh.Interators.sendtask.InteratorSendTask;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.general.general.LogHelper;
import ru.anit.anitfresh.metaobject.entities.Contractor;
import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.entities.User;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;
import ru.anit.anitfresh.ui.dialog.fielddialog.IDataItem;
import ru.anit.anitfresh.ui.dialog.fielddialog.IGetListDataItem;

/**
 * Created by Александр on 11.10.2016.
 */

public class PresenterFragmentDialogTask implements IPresenterFragmentDialogTask {

    Task mTask;

    IViewFragmentDialogTask view;

    public PresenterFragmentDialogTask(IViewFragmentDialogTask view, Task task) {
        this.view = view;

        if (task != null) {
            this.mTask = task;
        } else {
            mTask = new Task();
        }
    }


    @Override
    public void setContractor(Contractor contractor) {
        mTask.setGuidContractor(contractor.getGuid());
        view.setTextContractor(contractor.getName());
    }

    @Override
    public void setOtvetstvenniy(User user) {
        mTask.setGuidOtvetstvenniy(user.getGuid());
        view.setTextUser(user.getName());
    }

    @Override
    public void setKontroler(User user) {
        mTask.setGuidContraler(user.getGuid());
        view.setTextKontroler(user.getName());
    }

    @Override
    public void setTitle(String text) {
        mTask.setTitle(text);
    }

    @Override
    public void onSetPositiveButton() {

        //LogHelper.d(mTask.getJson().toString());
        IInterator sendTask = new InteratorSendTask(mTask);
        sendTask.execute();

    }

    @Override
    public IGetListDataItem getListContractor() {

        IGetListDataItem getListDataItem = new IGetListDataItem() {
            @Override
            public List<IDataItem> getList() {
                Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_TYPE + "=?", new String[]{
                        TYPE_ENTITIES.CONTRACTOR.getNameFromBase()}, null);

                List<IDataItem> list = new ArrayList<>();

                if (cursor != null && cursor.moveToFirst())

                {
                    do {

                        ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);

                        IDataItem item = (IDataItem) BuilderHelper.getObject(objectSyncTable);

                        list.add(item);


                    } while (cursor.moveToNext());
                }

                if (cursor != null) {
                    cursor.close();
                }

                return list;
            }
        };

        return getListDataItem;


    }

    @Override
    public IGetListDataItem getListUser() {
        IGetListDataItem getListDataItem = new IGetListDataItem() {
            @Override
            public List<IDataItem> getList() {
                Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_TYPE + "=?", new String[]{
                        TYPE_ENTITIES.USER.getNameFromBase()}, null);

                List<IDataItem> list = new ArrayList<>();

                if (cursor != null && cursor.moveToFirst())

                {
                    do {

                        ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);

                        IDataItem item = (IDataItem) BuilderHelper.getObject(objectSyncTable);

                        list.add(item);


                    } while (cursor.moveToNext());
                }

                if (cursor != null) {
                    cursor.close();
                }

                return list;
            }
        };

        return getListDataItem;
    }
}
