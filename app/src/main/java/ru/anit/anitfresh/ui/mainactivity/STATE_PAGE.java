package ru.anit.anitfresh.ui.mainactivity;

/**
 * Экраны
 */

public enum STATE_PAGE {



    TASKS {
        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getPageName() {
            return "Задачи";
        }
    }, USERS {
        @Override
        public int getId() {
            return 1;
        }

        @Override
        public String getPageName() {
            return "Пользователи";
        }
    }, CONTRACTORS {
        @Override
        public int getId() {
            return 2;
        }

        @Override
        public String getPageName() {
            return "Контрагенты";
        }
    };


    public abstract int getId();
    public abstract String getPageName();

    public static STATE_PAGE getState(int page) {

        switch (page) {
            case 0:
                return TASKS;
            case 1:
                return USERS;
            case 2:
                return CONTRACTORS;
            default:
                return TASKS;

        }

    }

}
