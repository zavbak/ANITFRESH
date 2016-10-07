package ru.anit.anitfresh.databus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 79900 on 17.09.2016.
 */
public class EventBusMessageOnInform implements IEventMessage {

    String messageText;

    public EventBusMessageOnInform(String messageText) {
        this.messageText = messageText;
    }

    public static void sendMessage(String messageText){
        new EventBusMessageOnInform(messageText).send();
    }

    public String getMessageText() {
        return messageText;
    }


    @Override
    public void send() {
        EventBus.getDefault().post(this);
    }


}
