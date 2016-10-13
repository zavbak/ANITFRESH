package ru.anit.anitfresh.metaobject.entities;


import ru.anit.anitfresh.ui.dialog.fielddialog.IDataItem;

/**
 * Created by 79900 on 17.09.2016.
 */
public abstract class MetaObject implements IAddCashRefs,IDataItem {

    String guid;
    TYPE_ENTITIES type;


    public String getGuid() {
        return guid;
    }

    public TYPE_ENTITIES getType() {
        return type;
    }

    public MetaObject(TYPE_ENTITIES type) {
        this.type = type;
    }

    protected MetaObject(String guid, TYPE_ENTITIES type) {
        this.guid = guid;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MetaObject{" +
                "guid='" + guid + '\'' +
                ", type=" + type +
                '}';
    }
}
