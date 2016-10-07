package ru.anit.anitfresh.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import ru.anit.anitfresh.data.database.DataBaseHelper;
import ru.anit.anitfresh.data.database.ObjectSyncTable;


/**
 * Created by 79900 on 25.08.2016.
 */
public class Provider extends ContentProvider {


    public static final String AUTHORITY = "ru.anit.anit.provider";

    static final String TABLE_SYNC = "table_sync";
    static final String QUERY_MAX_NUMBER = "querymaxnumber";
    static final String QUERY_SELECT_GUIDS = "queryselectguids";

    public static final Uri URI_TABLE_SYNC = Uri.parse("content://" + AUTHORITY + "/" + TABLE_SYNC);
    public static final Uri URI_QUERY_MAX_NUMBER = Uri.parse("content://" + AUTHORITY + "/" + QUERY_MAX_NUMBER);
    public static final Uri URI_QUERY_SELECT_GUIDS = Uri.parse("content://" + AUTHORITY + "/" + QUERY_SELECT_GUIDS);


    static final int FLAG_CYNC  = 1;
    static final int FLAG_QUERY_MAX_NUMBER = 2;
    static final int FLAG_QUERY_SELECT_GUIDS = 3;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_SYNC, FLAG_CYNC);
        uriMatcher.addURI(AUTHORITY, QUERY_MAX_NUMBER, FLAG_QUERY_MAX_NUMBER);
        uriMatcher.addURI(AUTHORITY, QUERY_SELECT_GUIDS, FLAG_QUERY_SELECT_GUIDS);
    }


    @Override
    public boolean onCreate() {
        return true;
    }


    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case FLAG_CYNC:

                cursor = DataBaseHelper.getInstance().query(ObjectSyncTable.TABLE_NAME, projection, selection, selectionArgs, sortOrder);

                break;
            case FLAG_QUERY_MAX_NUMBER:

                cursor = DataBaseHelper.getInstance().rawQuery("select * from  item_obj ORDER BY -number limit 1", null);
                break;
            case FLAG_QUERY_SELECT_GUIDS:

                cursor = DataBaseHelper.getInstance().rawQuery("select * from  item_obj where guid in (" +  makePlaceholders(selectionArgs.length) + ")", selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);

        }


        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (uriMatcher.match(uri)) {
            case FLAG_CYNC:

                long rowID = DataBaseHelper.getInstance().insert(ObjectSyncTable.TABLE_NAME, contentValues);
                Uri resultUri = ContentUris.withAppendedId(uri, rowID);

                notifyChanges(uri);

                return resultUri;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);

        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case FLAG_CYNC:

                int cnt = DataBaseHelper.getInstance().delete(ObjectSyncTable.TABLE_NAME, selection, selectionArgs);
                notifyChanges(uri);

                return cnt;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);

        }
    }




    @Override
    public int update(Uri uri,ContentValues contentValues, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case FLAG_CYNC:
                int cnt = DataBaseHelper.getInstance().update(ObjectSyncTable.TABLE_NAME,contentValues, selection, selectionArgs);
                notifyChanges(uri);

                return cnt;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
    }


    private void notifyChanges(Uri uri) {
        if (getContext() != null && getContext().getContentResolver() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}
