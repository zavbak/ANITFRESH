package ru.anit.anitfresh.Interators.puttoken;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import ru.anit.anitfresh.Interators.IInterator;
import ru.anit.anitfresh.data.ksoap.ExeptionKsoapApi;
import ru.anit.anitfresh.data.ksoap.RequestAPI;
import ru.anit.anitfresh.data.preferense.PreferenceHelper;
import ru.anit.anitfresh.databus.EventBusMessageOnError;
import ru.anit.anitfresh.general.general.LogHelper;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 07.10.2016.
 */

public class InteratorPutToken implements IInterator{


    /**
     *
     * @return
     * @throws JSONException
     */
    private static JSONObject getRequestParam() throws JSONException {

        // Действия которые надо выполнить
        //
        String COMMAND = "connand";

        // Команда добавить token устройства
        //
        String COMMAND_PUT_TOKEN = "put_token";

        String TOKEN = "token";

        String token = FirebaseInstanceId.getInstance().getToken();


        JSONObject jsonObject = new JSONObject();

        jsonObject.put(COMMAND, COMMAND_PUT_TOKEN);
        jsonObject.put(TOKEN, token);

        return jsonObject;

    }

    /**
     * получаем данные либо null
     *
     * @return
     */
    private static boolean put() {

        JSONObject paramJ = null;
        try {
            paramJ = getRequestParam();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String strParam = paramJ.toString();

        String response = null;


        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    subscriber.onNext(RequestAPI.getData(PreferenceHelper.getSettingReqestConnection(), strParam));
                } catch (ExeptionKsoapApi exeptionKsoapApi) {
                    exeptionKsoapApi.printStackTrace();
                    subscriber.onError(exeptionKsoapApi);
                }
                subscriber.onCompleted();
            }
        });

        Subscription subscription = stringObservable
                .subscribeOn(Schedulers.io()) //делаем запрос, преобразование, кэширование в отдельном потоке
                .observeOn(AndroidSchedulers.mainThread()) // обработка результата - в main thread
                .subscribe(s -> {
                    LogHelper.d(s);

                }, throwable -> {

                    LogHelper.e(throwable.getMessage());
                    EventBusMessageOnError.sendMessage("не передали token: " + throwable.getMessage());


                });


        return true;
    }


    @Override
    public boolean execute() {


        put();

        return true;
    }

}
