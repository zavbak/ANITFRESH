package ru.anit.anitfresh.ui.page.catalogs;


import ru.anit.anitfresh.metaobject.entities.TYPE_ENTITIES;

/**
 * Created by Александр on 28.09.2016.
 */

public enum CATALOGS {

    USERS {
        @Override
        String getName() {
            return "Пользователи";
        }

        @Override
        TYPE_ENTITIES getType_entities() {
            return TYPE_ENTITIES.USER;
        }
    },CONTRACTORS {
        @Override
        String getName() {
            return "Контрагенты";
        }

        @Override
        TYPE_ENTITIES getType_entities() {
            return TYPE_ENTITIES.CONTRACTOR;
        }
    };

    abstract String getName();
    abstract TYPE_ENTITIES getType_entities();
}
