package ru.anit.anitfresh.data.database;

import android.database.Cursor;

/**
 * Created by Александр on 02.08.2016.
 */
public class DataBaseUtils {

    /**
     *  Строку из курсора
     * @param cursor
     * @param fildName
     * @return
     */
    public static String getString(Cursor cursor, String fildName){
        int idx    = cursor.getColumnIndex(fildName);
        return cursor.getString(idx);
    }

    /**
     *  число из курсора
     * @param cursor
     * @param fildName
     * @return
     */
    public static int getInt(Cursor cursor, String fildName){
        int idx    = cursor.getColumnIndex(fildName);
        return cursor.getInt(idx);
    }


    /**
     *  лонг из курмора
     * @param cursor
     * @param fildName
     * @return
     */
    public static long getLong(Cursor cursor, String fildName){
        int idx    = cursor.getColumnIndex(fildName);
        return cursor.getLong(idx);
    }
}
