package ru.anit.anitfresh.data.preferense;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ru.anit.anitfresh.R;
import ru.anit.anitfresh.application.App;
import ru.anit.anitfresh.data.ksoap.SettingsSoap;


/**
 * Created by Александр on 30.06.2016.
 */
public class PreferenceHelper {


    /**
     *  Load setting for
     * @return
     */

    public static SettingsSoap getSettingReqestConnection(){

        Context context = App.getContext();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (context);
        prefs = PreferenceManager.getDefaultSharedPreferences (context);
        String hoast = prefs.getString(context.getString(R.string.pref_exchange_host_key),"");
        String user  = prefs.getString(context.getString(R.string.pref_exchange_user_key),"");
        String pass  = prefs.getString(context.getString(R.string.pref_exchange_pass_key),"");


        SettingsSoap settingsSoap = new SettingsSoap(hoast,user,pass);

        return settingsSoap;

    }

    /**
     * Устанавливаем User
     * @param strJsonUser
     */
    public static void saveUser(String strJsonUser){

        Context context = App.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (context);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(context.getString(R.string.pref_guid_user),strJsonUser);

        editor.commit();

    }


    /**
     * Возвращаем user
     * @return
     */
    public static String getUser(){

        Context context = App.getContext();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences (context);
        prefs = PreferenceManager.getDefaultSharedPreferences (context);
        String strJsonUser = prefs.getString(context.getString(R.string.pref_guid_user),null);


        return strJsonUser ;

    }





}
