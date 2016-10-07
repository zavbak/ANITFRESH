package ru.anit.anitfresh.databus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 79900 on 17.09.2016.
 */
public class EventBusMessageOnChangedData implements IEventMessage {


    public EventBusMessageOnChangedData() {

    }

    public static void sendMessage(){
        new EventBusMessageOnChangedData().send();
    }




    @Override
    public void send() {
        EventBus.getDefault().post(this);
    }


}
