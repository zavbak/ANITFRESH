package ru.anit.anitfresh.ui.page.tasks.lists;

/**
 * Created by Александр on 28.09.2016.
 */

public enum  TASKS {

    DO {
        @Override
        public String getName() {
            return "Делаю";
        }
    },CONTROL {
        @Override
        public String getName() {
            return "Контролирую";
        }
    };


    public abstract String getName();




}
