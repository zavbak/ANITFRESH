package ru.anit.anitfresh.Interators.synchronizer.helpers;

import android.content.Context;

import org.json.JSONObject;

import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.preferense.PreferenceHelper;
import ru.anit.anitfresh.data.provider.Provider;


/**
 *  Заменяем user
 */
public class ChangeUserHelper {

    /**
     *  заменить
     * @param userJ
     */
    public static void change(JSONObject userJ){


        Context context = App.getContext();
        context.getContentResolver().delete(Provider.URI_TABLE_SYNC, null, null);

        PreferenceHelper.saveUser(userJ.toString());

    }

}
