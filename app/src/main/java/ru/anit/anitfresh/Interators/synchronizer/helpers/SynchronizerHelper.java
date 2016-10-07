package ru.anit.anitfresh.Interators.synchronizer.helpers;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionParseJson;
import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionReceivedData;
import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionSaveDate;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.database.ObjectSyncTable;
import ru.anit.anitfresh.data.ksoap.ExeptionKsoapApi;
import ru.anit.anitfresh.data.ksoap.RequestAPI;
import ru.anit.anitfresh.data.preferense.PreferenceHelper;
import ru.anit.anitfresh.data.provider.Provider;
import ru.anit.anitfresh.databus.EventBusMessageOnChangedData;
import ru.anit.anitfresh.databus.EventBusMessageOnError;
import ru.anit.anitfresh.databus.EventBusMessageOnInform;
import ru.anit.anitfresh.general.general.LogHelper;
import ru.anit.anitfresh.metaobject.entities.MetaObject;
import ru.anit.anitfresh.metaobject.entities.User;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;
import ru.anit.anitfresh.nitification.NotifHelper;


public class SynchronizerHelper {


    /**
     * max номер записи
     *
     * @return
     */
    private static int getMax() {

        int number = 0;
        Cursor cursor = App.getContext().getContentResolver().query(Provider.URI_QUERY_MAX_NUMBER, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                ObjectSyncTable itemCatalog = new ObjectSyncTable(cursor);

                number = itemCatalog.getNumber();
                break;

            } while (cursor.moveToNext());
        }
        cursor.close();

        return number;

    }

    /**
     * start synchronizer
     */
    public static void start(Boolean notyfi) {
        Boolean repeat = true;
        List<MetaObject> listNotify = new ArrayList<>();

        try {

            int i = 0;
            while (repeat) {

                i++;
                repeat = GetPortionHelper.getPortion(getMax(), listNotify);

                EventBusMessageOnInform.sendMessage("dose Ok: " + i);
                LogHelper.e("dose Ok: " + i);

                if (!repeat) {


                    LogHelper.e("Synchronize Ok");
                    EventBusMessageOnInform.sendMessage("Synchronize Ok");
                    EventBusMessageOnChangedData.sendMessage();
                }
            }

        } catch (Throwable e) {

            LogHelper.e("Синхронизация остановлена: " + e.getMessage());
            EventBusMessageOnError.sendMessage("Синхронизация остановлена: " + e.getMessage());

        }

        if (notyfi) {
            if (listNotify.size() != 0) {
                NotifHelper.sendNotif();
            }
        }


    }

}