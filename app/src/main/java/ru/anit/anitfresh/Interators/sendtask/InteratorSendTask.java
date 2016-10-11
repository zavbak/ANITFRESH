package ru.anit.anitfresh.Interators.sendtask;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import ru.anit.anitfresh.Interators.IInterator;
import ru.anit.anitfresh.data.ksoap.ExeptionKsoapApi;
import ru.anit.anitfresh.data.ksoap.RequestAPI;
import ru.anit.anitfresh.data.preferense.PreferenceHelper;
import ru.anit.anitfresh.databus.EventBusMessageOnError;
import ru.anit.anitfresh.databus.EventBusMessageOnInform;
import ru.anit.anitfresh.general.general.LogHelper;
import ru.anit.anitfresh.metaobject.entities.Task;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 07.10.2016.
 */

public class InteratorSendTask implements IInterator{

    Task mTask;

    public InteratorSendTask(Task task) {
        this.mTask = task;
    }

    /**
     *
     * @return
     * @throws JSONException
     */
    private JSONObject getRequestParam() throws JSONException {

        // Действия которые надо выполнить
        //
        String COMMAND = "command";

        // Команда добавить token устройства
        //
        String COMMAND_SEND_TASK = "save_task";

        String TASK = "task";

        String token = FirebaseInstanceId.getInstance().getToken();


        JSONObject jsonObject = new JSONObject();

        jsonObject.put(COMMAND, COMMAND_SEND_TASK);
        jsonObject.put(TASK, mTask.getJson().toString());

        return jsonObject;

    }

    /**
     * получаем данные либо null
     *
     * @return
     */
    private  boolean send() {

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
                    EventBusMessageOnInform.sendMessage("передали задачу!");
                    LogHelper.d(s);

                }, throwable -> {

                    LogHelper.e(throwable.getMessage());
                    EventBusMessageOnError.sendMessage("не передали pflfxe: " + throwable.getMessage());


                });


        return true;
    }


    @Override
    public boolean execute() {

        send();

        return true;
    }

}
