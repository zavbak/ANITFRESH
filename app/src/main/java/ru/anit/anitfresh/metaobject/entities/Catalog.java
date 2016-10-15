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

        String NAME        = "name";
        String CODE        = "code";
        String IS_GROUP    = "is_group";


        this.name = jsonObject.getString(NAME);
        this.code = jsonObject.getString(CODE);

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
