package ru.anit.anitfresh.metaobject.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  справочник
 */
public abstract class Catalog extends MetaObject {

    String name;
    String code;

    protected Catalog(String guid, TYPE_ENTITIES type, JSONObject jsonObject) throws JSONException {
        super(guid, type);

        String VALUE_TAG       = "#value";
        String OBJECT_JSON_TAG = "objectJson";

        String DESCRIPTION_TAG = "Description";
        String CODE_TAG        = "Code";


        JSONObject valuej      = new JSONObject(jsonObject.getString(VALUE_TAG));

        this.name = valuej.getString("Description");
        this.code = valuej.getString("Code");

    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public Boolean filter(String strSearch) {
        String up_search = strSearch.toUpperCase();

        String up_name  = getName().toUpperCase();
        if(up_name.contains(up_search)){

            return true;

        }else {
            return false;
        }
    }
}
