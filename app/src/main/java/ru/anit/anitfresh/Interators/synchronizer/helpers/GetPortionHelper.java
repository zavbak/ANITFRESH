package ru.anit.anitfresh.Interators.synchronizer.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionParseJson;
import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionReceivedData;
import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionSaveDate;
import ru.anit.anitfresh.data.ksoap.ExeptionKsoapApi;
import ru.anit.anitfresh.data.preferense.PreferenceHelper;
import ru.anit.anitfresh.metaobject.entities.MetaObject;
import ru.anit.anitfresh.metaobject.entities.User;
import ru.anit.anitfresh.metaobject.helpers.BuilderHelper;

/**
 * Created by Александр on 07.10.2016.
 */

public class GetPortionHelper {


    /**
     * Получаем User из ответа либо null
     *
     * @param jsonObject
     * @return
     */
    private static JSONObject getUserOutJson(JSONObject jsonObject) throws ExeptionParseJson {

        String USER_TAG = "user";

        try {

            String userOutStr = jsonObject.getString(USER_TAG);
            JSONObject userOutJson = new JSONObject(userOutStr);

            return userOutJson;

        } catch (JSONException e) {
            e.printStackTrace();
            throw new ExeptionParseJson("не смогли получить User Из ответа");
        }

    }

    /**
     * возвращаем user Json из Preference либо null
     *
     * @return
     */
    private static JSONObject getUserPreferenceJson() {


        String userInStr = PreferenceHelper.getUser();

        if (userInStr == null) {
            return null;
        }

        try {
            return new JSONObject(userInStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Проверяем надо ли менять User
     *
     * @param outUserJ
     * @param inUserJ
     * @return
     * @throws Throwable
     */
    private static Boolean isCangeUser(JSONObject outUserJ, JSONObject inUserJ) {

        if (inUserJ == null) {
            return true;
        }

        if (outUserJ.toString().equals(inUserJ.toString())) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * получаем блок
     *
     * @param number
     * @return
     */
    public static boolean getPortion(int number, List<MetaObject> listNotify) throws ExeptionReceivedData, ExeptionParseJson, ExeptionSaveDate, ExeptionKsoapApi {

        String RESPONSE = "response";

        // получаем данные может выкинуть ExeptionReceivedData, ExeptionParseJson
        JSONObject data = GetDataApiHelper.getData(number);

        JSONObject responseJ = data.optJSONObject(RESPONSE);

        if(responseJ == null){
            throw new ExeptionParseJson("нет тега response!");
        }


        // получаем Use может выкинуть  ExeptionParseJson
        JSONObject userOutJ = getUserOutJson(responseJ);

        // получаем Use может null
        JSONObject inUserJ = getUserPreferenceJson();

        // если true меняем данныве и User
        Boolean isCangeUser = isCangeUser(userOutJ, inUserJ);


        if (isCangeUser) {
            // сохранили usera и продолжаем
            ChangeUserHelper.change(userOutJ);
            return true;

        }

        User user = null;

        try {
            user = (User) BuilderHelper.getObject(userOutJ);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ExeptionParseJson("ошибка чтения пользователя из настроек!");
        }


        JSONArray valueArrJ = null;
        try {
            valueArrJ = responseJ.getJSONArray("array_data");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ExeptionParseJson("не блока array_data");
        }

        if (valueArrJ.length() == 0) {
            return false; // нет данных
        }

        List<MetaObject> list = SaveHelper.save(valueArrJ,user);

        for(MetaObject item:list){

            listNotify.add(item);

        }

        return true;
    }

}

