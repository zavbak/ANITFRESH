package ru.anit.anitfresh.metaobject.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

/**
 * Created by 79900 on 17.09.2016.
 */
public class Task extends Document {


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

        JSONObject value = new JSONObject(jsonObject.getString("#value"));

        this.title = value.getString("ОписаниеСобытия");
        this.endTask = value.getString("ОкончаниеСобытия");

        guidContractor = value.getString("Контрагент");
        guidOtvetstvenniy = value.getString("Ответственный");
        guidPostanovchic = value.getString("ПостановщикЗадачи");
        guidContraler = value.getString("Контроллер");

    }


    public JSONObject getJson() throws JSONException {

        final String TITLE              = "title";
        final String GUID_CONTRACTOR    = "guid_contractor";
        final String GUID_OTVETSTVENNIY = "guid_otvetstvenniy";

        JSONObject taskJ = new JSONObject();
        taskJ.put(TITLE,title);
        taskJ.put(GUID_CONTRACTOR,guidContractor);
        taskJ.put(GUID_OTVETSTVENNIY,guidOtvetstvenniy);

        return taskJ;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGuidOtvetstvenniy(String guidOtvetstvenniy) {
        this.guidOtvetstvenniy = guidOtvetstvenniy;
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
