package ru.anit.anitfresh.metaobject.helpers;

import org.json.JSONException;
import org.json.JSONObject;

import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.metaobject.entities.Contractor;
import ru.anit.anitfresh.metaobject.entities.MetaObject;
import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;
import ru.anit.anitfresh.metaobject.entities.Task;
import ru.anit.anitfresh.metaobject.entities.User;


/**
 * Created by 79900 on 17.09.2016.
 */
public class BuilderHelper {

    private static MetaObject build(TYPE_ENTITIES type, ObjectSyncTable objectSyncTable) throws JSONException {

        JSONObject jsonObject = new JSONObject(objectSyncTable.getObj_json());

        switch (type) {

            case USER:
                return new User(objectSyncTable.getGuid(), jsonObject);
            case CONTRACTOR:
                return new Contractor(objectSyncTable.getGuid(), jsonObject);
            case TASK:
                return new Task(objectSyncTable.getGuid(), jsonObject);
            default:
                return null;

        }
    }


    /**
     * Создаем мета объект
     *
     * @param objectSyncTable
     * @return
     */
    public static MetaObject getObject(ObjectSyncTable objectSyncTable) {
        TYPE_ENTITIES type = TYPE_ENTITIES.getType(objectSyncTable.getTypeString());
        try {
            return build(type, objectSyncTable);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param json
     * @return
     */
    public static MetaObject getObject(JSONObject json) throws JSONException {


        JSONObject jsonObject = new JSONObject(json.getString("objectJson"));
        TYPE_ENTITIES type = TYPE_ENTITIES.getType(jsonObject.getString("#type"));
        JSONObject value = new JSONObject(jsonObject.getString("#value"));
        String guid = value.getString("Ref");


        switch (type) {

            case USER:
                return new User(guid, jsonObject);
            case CONTRACTOR:
                return new Contractor(guid, jsonObject);
            case TASK:
                return new Task(guid, jsonObject);
            default:
                return null;

        }


    }


}
