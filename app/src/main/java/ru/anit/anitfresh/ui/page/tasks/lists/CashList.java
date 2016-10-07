package ru.anit.anitfresh.ui.page.tasks.lists;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.preferense.PreferenceHelper;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.general.general.DataUtils;
import ru.anit.anitfresh.metaobject.entities.MetaObject;
import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.entities.User;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;


/**
 * Created by Александр on 21.09.2016.
 */
public class CashList {

    List<Task> list;
    HashMap<String, MetaObject> objectHashMap;
    TASKS type;

    public CashList(TASKS type) throws JSONException {
        this.type = type;
        fill();
    }

    public List<Task> getList() {
        return list;
    }

    public HashMap<String, MetaObject> getObjectHashMap() {
        return objectHashMap;
    }

    /**
     * Фильтруем задачи
     *
     * @param task
     * @param user
     * @return
     */
    private Boolean filter(Task task, User user) {

        switch (type) {

            case DO:

                if (!user.getGuid().equals(task.getGuidOtvetstvenniy())) {
                    return false;
                }
                break;
            case CONTROL:

                if (!user.getGuid().equals(task.getGuidContraler())) {
                    return false;
                }
                break;
        }


        return true;

    }


    /**
     * делаем запрос и кэшируем объекты
     *
     * @param stringHashSet
     */
    private void fillObjectHashMap(HashSet<String> stringHashSet) {


        objectHashMap = new HashMap<>();

        if (stringHashSet.size() != 0) {



            Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_QUERY_SELECT_GUIDS, null, null, (String[]) stringHashSet.toArray(new String[0]), null);

            if (cursor != null && cursor.moveToFirst())

            {
                do {

                    ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);
                    MetaObject item = BuilderHelper.getObject(objectSyncTable);

                    objectHashMap.put(item.getGuid(), item);


                } while (cursor.moveToNext());
            }

            if(cursor != null){
                cursor.close();
            }

        }


    }


    /**
     * Заполняем
     *
     * @throws JSONException
     */
    private void fill() throws JSONException {

        List<Task> listTask = new ArrayList<>();
        HashSet<String> stringHashSet = new HashSet<>();

        User user = null;
        String userStr = PreferenceHelper.getUser();
        if (userStr != null) {

            user = (User) BuilderHelper.getObject(new JSONObject(userStr));


            Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_TABLE_SYNC, null, ObjectSyncTable.FIELD_TYPE + "=?", new String[]{
                    TYPE_ENTITIES.TASK.getNameFromBase()}, null);


            if (cursor != null && cursor.moveToFirst())

            {
                do {

                    ObjectSyncTable objectSyncTable = new ObjectSyncTable(cursor);
                    Task item = (Task) BuilderHelper.getObject(objectSyncTable);


                    if (!filter(item, user)) {
                        continue;
                    }

                    item.addRefs(stringHashSet);

                    listTask.add(item);


                } while (cursor.moveToNext());
            }


            Collections.sort(listTask, COMPARE_BY_DATA_END);

        }


        // заполняем кэш
        fillObjectHashMap(stringHashSet);

        // заполняем список
        this.list = listTask;


    }

    /**
     * Сортировка по дате
     */
    static final Comparator<Task> COMPARE_BY_DATA_END = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {

            long d1 = DataUtils.dataJsonToLong(t1.getEndTask());
            long d2 = DataUtils.dataJsonToLong(t2.getEndTask());

            if (d1 < d2) {
                return -1;
            } else if (d1 > d2) {
                return 1;
            } else {
                return 0;
            }
        }
    };


}
