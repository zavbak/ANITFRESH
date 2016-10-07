package ru.anit.anitfresh.metaobject.entities;

public enum TYPE_ENTITIES {

    TASK {
        @Override
        public String getNameFromBase() {
            return "jcfg:DocumentObject.Событие";
        }

        @Override
        public String getName() {
            return "Задачи";
        }
    },CONTRACTOR {
        @Override
        public String getNameFromBase() {
            return "jcfg:CatalogObject.Контрагенты";
        }

        @Override
        public String getName() {
            return "Контрагенты";
        }
    },USER {
        @Override
        public String getNameFromBase() {
            return "jcfg:CatalogObject.Пользователи";
        }

        @Override
        public String getName() {
            return "Сотрудники";
        }
    };

    public abstract String getNameFromBase();
    public abstract String getName();


    public static TYPE_ENTITIES getType(String strType){

      String str = strType.toUpperCase();

        if(str.equals(TASK.getNameFromBase().toUpperCase())){
           return TASK;
        }else if(str.equals(CONTRACTOR.getNameFromBase().toUpperCase())){
            return CONTRACTOR;

        }else if(str.equals(USER.getNameFromBase().toUpperCase())){
            return USER;
        }

        return null;

    }

}
