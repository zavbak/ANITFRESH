package ru.anit.anitfresh.data.ksoap;

/**
 * Created by User on 05.01.2015.
 */
public class SettingsSoap {


    /**
     * IP или Адрес можно с портом 212.19.2.67:5566
     */
    private String host;

    /**
     * Название базы при публикации на вебсервисе из 1С
     */
    private String nameBase;

    /**
     * Название операции в 1С
     */
    private String operation;
    /**
     * Пользователь в 1С
     */
    private String user;
    /**
     * Пароль в 1С
     */
    private String password;

    /**
     * Название Вебсервиса в 1С
     */
    private String nameWebServises;

    /**
     * Uri пространство имен в вебсервисе 1С  например http://www.799000.ru/Mobile
     */
    private String nameSpace;

    /**
     * Тип возвоащаемого значения Может быть Soap например "http://www.799000.ru/Mobile/TABLEGOODS"
     */
    private String soapAction;


    public String getHost() {
        return host;
    }

    public String getNameBase() {
        return nameBase;
    }

    public String getOperation() {
        return operation;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getNameWebServises() {
        return nameWebServises;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public String getSoapAction() {
        return soapAction;
    }



    /**
     * URL "http://" + host + "/" + nameBase + "/ws/" + nameWebServises + ".1cws";
     * @return
     */
    public String getUrl() {
        return "http://" + host + "/" + nameBase + "/ws/" + nameWebServises + ".1cws";
    }


    public SettingsSoap(String host, String user, String password) {
        this.host     = host;
        this.password = password;
        this.user     = user;

        this.nameBase        = "upp_anit";
        this.operation       = "interchange";
        this.nameWebServises = "wsAnit";
        nameSpace            = "www.URI.799000.ru";
        soapAction           = "http://www.w3.org/2001/XMLSchema/string";


    }

    /**
     * Начальные настройки
     */
    public SettingsSoap() {


        //host     = "192.168.1.220";
        host     = "192.168.1.220";

        nameBase = "upp_anit";

        user     = "Гладких";

        password = "";

        operation = "interchange";

        nameWebServises = "wsAnit";

        nameSpace = "www.URI.799000.ru";

        //soapAction1C = "http://www.799000.ru/anit/Package";
        soapAction = "http://www.w3.org/2001/XMLSchema/string";


    }


}
