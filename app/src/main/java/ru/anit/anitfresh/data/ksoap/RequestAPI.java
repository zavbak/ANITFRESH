package ru.anit.anitfresh.data.ksoap;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import ru.anit.anitfresh.general.general.LogHelper;
import rx.Observable;
import rx.exceptions.Exceptions;

/**
 * Created by Александр on 07.06.2016.
 */
public class RequestAPI {


    SettingsSoap settingsSoap;

    private RequestAPI(SettingsSoap settingsSoap) {
        this.settingsSoap = settingsSoap;
    }


    public static String getData(SettingsSoap settingsSoap, String strJson) throws ExeptionKsoapApi {

        //String request = new RequestAPI(settingsSoap).call(strJson);


        String responseStr = null;
        try {
            responseStr = Observable.just(strJson)
                    .concatMap(s -> {
                        try {

                            LogHelper.d("send request " + new Date() + ":" + strJson);
                            String response = new RequestAPI(settingsSoap).call(strJson);
                            LogHelper.d("Response received " + new Date() + ":" + response.length());
                            return Observable.just(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                            throw Exceptions.propagate(e);
                        }

                    })

                    .doOnError(throwable -> LogHelper.e("Response error " + new Date() + ":" + throwable.getMessage()))
                    .timeout(40, TimeUnit.SECONDS)
                    .retry(2)
                    .doOnError(throwable -> {
                        LogHelper.e("Send will in event bus: " + throwable);
                    })
                    .toBlocking()
                    .single();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExeptionKsoapApi(e.getMessage());
        }

        return responseStr;

    }


    /**
     * @return
     */
    private SoapSerializationEnvelope getEnvelope(SettingsSoap settingsSoap, String strJson) {

        SoapObject request = new SoapObject(settingsSoap.getNameSpace(),
                settingsSoap.getOperation());

        request.addProperty("stringJSON", strJson);  //Добавляем свойства

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = request;


        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        return envelope;
    }


    /**
     * Непосредственно вызов
     */
    private String call(String strJson) throws IOException, XmlPullParserException {

        //SettingsSoap settingsSoap = new SettingsSoap();


        //Установим URL, Пользователя и Пароль
        HttpTransportSE androidHttpTransport = new HttpTransportBasicAuthSE(settingsSoap.getUrl(),
                settingsSoap.getUser(), settingsSoap.getPassword());

        //Установим настройки
        SoapSerializationEnvelope envelope = getEnvelope(settingsSoap, strJson);


        androidHttpTransport.call(settingsSoap.getSoapAction(), envelope);
        return envelope.getResponse().toString();

    }

    /**
     * Это вспомогательный класс для прохождения пароля
     */
    private static class HttpTransportBasicAuthSE extends HttpTransportSE {
        private String username;
        private String password;

        /**
         * Constructor with username and password
         *
         * @param url      The url address of the webservice endpoint
         * @param username Username for the Basic Authentication challenge RFC 2617
         * @param password Password for the Basic Authentication challenge RFC 2617
         */
        public HttpTransportBasicAuthSE(String url, String username, String password) {
            super(url, 1000); //Это тайм аут в милесекундах
            this.username = username;
            this.password = password;
        }

        public ServiceConnection getServiceConnection() throws IOException {
            ServiceConnection midpConnection = new ServiceConnectionSE(url);
            addBasicAuthentication(midpConnection);
            return midpConnection;
        }

        protected void addBasicAuthentication(ServiceConnection midpConnection) throws IOException {
            if (username != null && password != null) {
                StringBuffer buf = new StringBuffer(username);
                buf.append(':').append(password);
                byte[] raw = buf.toString().getBytes();
                buf.setLength(0);
                buf.append("Basic ");
                org.kobjects.base64.Base64.encode(raw, 0, raw.length, buf);
                midpConnection.setRequestProperty("Authorization", buf.toString());
            }
        }

    }


}
