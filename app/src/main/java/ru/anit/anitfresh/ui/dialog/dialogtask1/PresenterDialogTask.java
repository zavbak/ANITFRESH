package ru.anit.anitfresh.ui.dialog.dialogtask1;

import android.content.ClipData;
import android.database.Cursor;
import android.os.Bundle;

import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.metaobject.entities.Catalog;
import ru.anit.anitfresh.metaobject.entities.MetaObject;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;
import ru.anit.anitfresh.ui.mainactivity.STATE_PAGE;

/**
 * Created by 79900 on 18.10.2016.
 */

public class PresenterDialogTask implements IPresenterDialogTask {

    Task task;
    IViewDialogTask view;

    String sGuid;




    public PresenterDialogTask(IViewDialogTask view) {
        this.view = view;
    }





    /**
     *
     * @param guid
     * @return
     */
    MetaObject getMetaObject(String guid){

        MetaObject item = null;

        Cursor cursor = App.getContext().getContentResolver().
                query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_GUID + "=?", new String[]{guid}, null);

        if (cursor != null && cursor.moveToFirst())

        {
            do {

                ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);

                item = BuilderHelper.getObject(objectSyncTable);
                break;


            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        if(item != null){
            return item;
        }

        return null;

    }



    /**
     *
     * @param guid
     * @return
     */
    String getCatalogName(String guid){


        Catalog item = (Catalog) getMetaObject(guid);

        if(item != null){
            return item.getName();
        }

        return "";
    }


    @Override
    public void onStart() {

        if(sGuid != null){
            task = (Task) getMetaObject(sGuid);
        }else {
            task = new Task();
        }


        if (task != null) {
            view.setTitle("Задача: № " + " от ");
            view.setOtvetstvenniy(getCatalogName(task.getGuidOtvetstvenniy()));
            view.setContractor(getCatalogName(task.getGuidContractor()));
            view.setContraler(getCatalogName(task.getGuidContraler()));
            view.setTitleTask(task.getTitle());
        } else {
            view.setTitle("Новая задача");
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void loadState(Bundle savedInstanceState) {

    }

    @Override
    public void saveState(Bundle savedInstanceState) {

    }

    @Override
    public void setGuid(String guid) {
        this.sGuid = guid;
    }
}
