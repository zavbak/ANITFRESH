package ru.anit.anitfresh.metaobject.entities;

import org.json.JSONObject;

/**
 * Created by 79900 on 17.09.2016.
 */
public abstract class Document extends MetaObject {


    protected Document(String guid, TYPE_ENTITIES type, JSONObject jsonObject) {
        super(guid, type);
    }


}
