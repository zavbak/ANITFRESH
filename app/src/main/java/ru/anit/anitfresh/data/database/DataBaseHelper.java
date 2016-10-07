package ru.anit.anitfresh.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.anit.anitfresh.application.App;


/**
 * Created by 79900 on 24.08.2016.
 */
public class DataBaseHelper {

    DBOpenHelper dbHelper;
    SQLiteDatabase db;

    private DataBaseHelper(Context context) {
        dbHelper = DBOpenHelper.getInstance(context);
    }

    public static DataBaseHelper getInstance(){
        return  new DataBaseHelper(App.getContext());
    }



    /**
     * Запрос
     * @param tableName
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    public Cursor query(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    /**
     * Добавить
     * @param tableName
     * @param contentValues
     * @return
     */
    public long insert(String tableName, ContentValues contentValues) {

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(tableName, null, contentValues);
        return rowID;
    }

    /**
     * Удалить
     * @param tableName
     * @param selection
     * @param selectionArgs
     * @return
     */
    public int delete(String tableName, String selection, String[] selectionArgs) {

        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(tableName, selection, selectionArgs);

        return cnt;

    }

    /**
     * Обновить
     * @param tableName
     * @param contentValues
     * @param selection
     * @param selectionArgs
     * @return
     */
    public int update(String tableName, ContentValues contentValues, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(tableName, contentValues, selection, selectionArgs);

        return cnt;
    }




    /**
     * Произвольный запрос
     * @return
     */
    public Cursor rawQuery(String strQuery,String[] selectionArgs) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery(strQuery,selectionArgs);
        return cursor;
    }



}
