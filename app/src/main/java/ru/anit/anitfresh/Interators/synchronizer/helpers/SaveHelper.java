package ru.anit.anitfresh.Interators.synchronizer.helpers;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionParseJson;
import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionSaveDate;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.metaobject.entities.MetaObject;
import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.entities.User;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;


/**
 * Created by 79900 on 17.09.2016.
 */
public class SaveHelper {

    /**
     * Сохранить данные
     *
     * @param valueArrJ
     */
    public static List<MetaObject> save(JSONArray valueArrJ, User user) throws ExeptionParseJson, ExeptionSaveDate {


        List<MetaObject> listNotify = new ArrayList<>();


        // кэшируем guid
        List<String> guids = new ArrayList<>();

        // кэшируем
        List<ObjectSyncTable> objectSyncTables = new ArrayList<>();


        for (int i = 0; i < valueArrJ.length(); i++) {
            try {

                JSONObject arrayElement = valueArrJ.getJSONObject(i);
                guids.add(arrayElement.getString("guid"));

                objectSyncTables.add(new ObjectSyncTable(arrayElement));


            } catch (JSONException e) {
                throw new ExeptionParseJson("ошибка получения элемена данных из массива ");
            }

        }


        // получаем объекты по guid которые уже есть
        Context context = App.getContext();
        Cursor cursor = context.getContentResolver().query(Provider.URI_QUERY_SELECT_GUIDS, null, null, (String[]) guids.toArray(new String[0]), null);


        // кэщируем
        HashMap<String, String> hashMap = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {

                ObjectSyncTable itemCatalog = new ObjectSyncTable(cursor);
                hashMap.put(itemCatalog.getGuid(), null);

            } while (cursor.moveToNext());
        }



        // опреации для транзакции в ContentProvider
        ArrayList<ContentProviderOperation> listOperations = new ArrayList<>();

        for (ObjectSyncTable obj : objectSyncTables) {

            ContentProviderOperation.Builder op;

            String guid = obj.getGuid();

            if (!hashMap.containsKey(guid)) {
                // Insert
                op = ContentProviderOperation.newInsert(Provider.URI_TABLE_SYNC)
                        .withValues(obj.getContentValues());


                if (newUserTask(obj,user)){
                    listNotify.add(BuilderHelper.getObject(obj)); // новые объекты для оповещения
                }


            } else {

                // update
                op = ContentProviderOperation.newUpdate(Provider.URI_TABLE_SYNC)
                        .withSelection(ObjectSyncTable.FIELD_GUID + "= ?", new String[]{guid})
                        .withValues(obj.getContentValues());
            }

            listOperations.add(op.build());

        }


        try {

            App.getContext().getContentResolver().applyBatch(Provider.AUTHORITY, listOperations);

        } catch (Exception e) {

            throw new ExeptionSaveDate(e.getMessage());
        }

        return listNotify;

    }


    static boolean newUserTask(ObjectSyncTable obj,User user){

        if(!obj.getFilter()){
            return false;
        }

        if(!obj.getTypeString().equals(TYPE_ENTITIES.TASK.getNameFromBase())){
            return false;
        }

        Task task = (Task) BuilderHelper.getObject(obj);

        if(task.getGuidOtvetstvenniy().equals(user.getGuid())){
            return true;
        }else {
            return false;
        }
    }


}
