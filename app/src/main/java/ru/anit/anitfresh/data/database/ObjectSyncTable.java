package ru.anit.anitfresh.data.database;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Александр on 07.09.2016.
 */
public class ObjectSyncTable implements ITableSync{

    long    id;
    String  guid;
    String  type;
    String  obj_json;
    int     number;
    Boolean filter;


    public ObjectSyncTable() {
    }

    public ObjectSyncTable(Cursor cursor) {

        id       = DataBaseUtils.getLong(cursor,FIELD_ID);
        guid     = DataBaseUtils.getString(cursor,FIELD_GUID);
        type     = DataBaseUtils.getString(cursor,FIELD_TYPE);
        obj_json = DataBaseUtils.getString(cursor,FIELD_OBJ_JSON);
        number   = DataBaseUtils.getInt(cursor,FIELD_NUMBER);

        if  (DataBaseUtils.getInt(cursor,FIELD_FILTER) == 1){
            filter = true;
        }else{
            filter = false;
        };
    }

    public ObjectSyncTable(JSONObject jsonObject) throws JSONException {

        this.guid   = jsonObject.getString("guid");
        this.number = jsonObject.getInt("number");
        this.filter = jsonObject.getBoolean("filter");

        if(this.filter){

            this.type       = jsonObject.getString("type");
            this.obj_json   = jsonObject.getString("objectJson");

        }else{

            this.type     = "";
            this.obj_json = "";

        }

    }


    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_GUID     , guid);
        cv.put(FIELD_TYPE     , type);
        cv.put(FIELD_OBJ_JSON , obj_json);
        cv.put(FIELD_NUMBER   , number);
        if(filter){
            cv.put(FIELD_FILTER, 1);
        }else {
            cv.put(FIELD_FILTER, 0);
        }
        return cv;
    }

    public long getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTypeString() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObj_json() {
        return obj_json;
    }

    public void setObj_json(String obj_json) {
        this.obj_json = obj_json;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Boolean getFilter() {
        return filter;
    }

    public void setFilter(Boolean filter) {
        this.filter = filter;
    }


    @Override
    public String toString() {
        return "ItemDBSync{" +
                "id=" + id +
                ", guid='" + guid + '\'' +
                ", type=" + type +
                ", obj_json='" + obj_json + '\'' +
                ", number=" + number +
                ", filter=" + filter +
                '}';
    }
}
