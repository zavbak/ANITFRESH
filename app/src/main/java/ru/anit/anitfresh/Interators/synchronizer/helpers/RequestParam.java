package ru.anit.anitfresh.Interators.synchronizer.helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestParam {

    /**
     * ПАраметры для запроса
     *
     * @return
     * @throws JSONException
     */
    public static JSONObject getRequestParam(int number) throws Exception {

        int count = 100;

        String COMMAND             = "command";
        String COMMAND_SYNCHRONIZE = "synchronize";

        String START_NUMBER    = "start_number";
        String QUNTITY         = "quantity";


        JSONObject jsonObject = new JSONObject();

        jsonObject.put(COMMAND,COMMAND_SYNCHRONIZE);
        jsonObject.put(START_NUMBER, number);
        jsonObject.put(QUNTITY, count);

        return jsonObject;

    }
}
