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
     * @param json из без number
     * @return
     */
    public static MetaObject getObject(JSONObject json) throws JSONException {



        TYPE_ENTITIES type    = TYPE_ENTITIES.getType(json.getString("type"));
        String guid           = json.getString("guid");


        switch (type) {

            case USER:
                return new User(guid, json);
            case CONTRACTOR:
                return new Contractor(guid, json);
            case TASK:
                return new Task(guid, json);
            default:
                return null;

        }


    }


}
