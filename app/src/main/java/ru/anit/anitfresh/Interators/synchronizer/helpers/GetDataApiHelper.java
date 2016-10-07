package ru.anit.anitfresh.Interators.synchronizer.helpers;

/**
 * Created by Александр on 07.10.2016.
 */

import org.json.JSONObject;

import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionParseJson;
import ru.anit.anitfresh.Interators.synchronizer.exeptionsynchronize.ExeptionReceivedData;
import ru.anit.anitfresh.data.ksoap.ExeptionKsoapApi;
import ru.anit.anitfresh.data.ksoap.RequestAPI;
import ru.anit.anitfresh.data.preferense.PreferenceHelper;

/**
 * Created by Александр on 06.10.2016.
 */

public class GetDataApiHelper {

    /**
     * получаем данные либо null
     *
     * @return
     */
    public static JSONObject getData(int number) throws ExeptionReceivedData, ExeptionParseJson, ExeptionKsoapApi {

        JSONObject paramJ = null;
        try {
            paramJ = RequestParam.getRequestParam(number);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExeptionReceivedData(e.getMessage());
        }

        String strParam = paramJ.toString();

        String response = null;

        response = RequestAPI.getData(PreferenceHelper.getSettingReqestConnection(), strParam);


        try {
            return new JSONObject(response);
        }  catch (Exception e) {
            throw new ExeptionParseJson("не смогли пропарсить строку из ответа");
        }

    }
}
