package ru.anit.anitfresh.data.database;

import android.content.ContentValues;

/**
 * Created by Александр on 07.09.2016.
 */
public interface ITableSync {

    public static final String TABLE_NAME = "item_obj";

    public static final String FIELD_ID        = "_id";
    public static final String FIELD_GUID      = "guid";
    public static final String FIELD_TYPE      = "type";
    public static final String FIELD_OBJ_JSON  = "obj_json";
    public static final String FIELD_NUMBER    = "number";
    public static final String FIELD_FILTER    = "filter";


    public static final String createTableStr = "create table " + TABLE_NAME + " ("
            + FIELD_ID + " integer primary key autoincrement,"
            + FIELD_GUID + " text,"
            + FIELD_TYPE + " text,"
            + FIELD_OBJ_JSON + " text,"
            + FIELD_FILTER   + " integer,"
            + FIELD_NUMBER   + " integer"

            + ");";

    ContentValues getContentValues();
}


