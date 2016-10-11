package ru.anit.anitfresh.metaobject.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

/**
 * Created by 79900 on 17.09.2016.
 */
public class Contractor extends Catalog {
    public Contractor(String guid, JSONObject jsonObject) throws JSONException {
        super(guid, TYPE_ENTITIES.CONTRACTOR, jsonObject);
    }

    @Override
    public void addRefs(HashSet<String> hashSetStr) {

    }

}


