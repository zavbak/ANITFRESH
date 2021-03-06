package ru.anit.anitfresh.metaobject.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

/**
 * Created by 79900 on 17.09.2016.
 */
public class Task extends Document {


    final String TITLE              = "title";
    final String GUID_CONTRACTOR    = "guid_contractor";
    final String GUID_OTVETSTVENNIY = "guid_otvetstvenniy";
    final String GUID_KONTRALER     = "guid_contraler";
    final String DATE_OKONCHANIA    = "date_okonchania";
    final String GUID_POSTANOVCHIC  = "guid_postanovchic";


    String title;
    String endTask;
    String guidContractor;
    String guidOtvetstvenniy;
    String guidPostanovchic;
    String guidContraler;

    public Task() {
        super(TYPE_ENTITIES.TASK);
    }

    public Task(String guid, JSONObject jsonObject) throws JSONException {
        super(guid, TYPE_ENTITIES.TASK, jsonObject);


        this.title = jsonObject.getString(TITLE);
        this.endTask = jsonObject.getString(DATE_OKONCHANIA);

        guidContractor    = jsonObject.getString(GUID_CONTRACTOR);
        guidOtvetstvenniy = jsonObject.getString(GUID_OTVETSTVENNIY);
        guidPostanovchic  = jsonObject.getString(GUID_POSTANOVCHIC);
        guidContraler     = jsonObject.getString(GUID_KONTRALER);

    }


    public JSONObject getJson() throws JSONException {



        JSONObject taskJ = new JSONObject();
        taskJ.put(TITLE,title);
        taskJ.put(GUID_CONTRACTOR,guidContractor);
        taskJ.put(GUID_OTVETSTVENNIY,guidOtvetstvenniy);
        taskJ.put(GUID_KONTRALER    ,guidContraler);

        return taskJ;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGuidOtvetstvenniy(String guidOtvetstvenniy) {
        this.guidOtvetstvenniy = guidOtvetstvenniy;
    }

    public void setGuidContraler(String guidContraler) {
        this.guidContraler = guidContraler;
    }

    public void setGuidContractor(String guidContractor) {
        this.guidContractor = guidContractor;
    }

    public String getTitle() {
        return title;
    }

    public String getEndTask() {
        return endTask;
    }

    public String getGuidContractor() {
        return guidContractor;
    }

    public String getGuidContraler() {
        return guidContraler;
    }

    public String getGuidOtvetstvenniy() {
        return guidOtvetstvenniy;
    }

    public String getGuidPostanovchic() {
        return guidPostanovchic;
    }

    public void setRefs(HashSet<String> hashSetStr) {
        hashSetStr.add(guidContractor);
        hashSetStr.add(guidOtvetstvenniy);
        hashSetStr.add(guidPostanovchic);
        hashSetStr.add(guidContraler);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", endTask='" + endTask + '\'' +
                ", guidContractor='" + guidContractor + '\'' +
                ", guidOtvetstvenniy='" + guidOtvetstvenniy + '\'' +
                ", guidPostanovchic='" + guidPostanovchic + '\'' +
                ", guidContraler='" + guidContraler + '\'' +
                '}';
    }

    @Override
    public void addRefs(HashSet<String> stringHashSet) {
        stringHashSet.add(guidContractor);
        stringHashSet.add(guidOtvetstvenniy);
        stringHashSet.add(guidPostanovchic);
        stringHashSet.add(guidContraler);
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public Boolean filter(String strSearch) {
        return true;
    }
}
