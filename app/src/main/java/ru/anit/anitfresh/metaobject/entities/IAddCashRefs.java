package ru.anit.anitfresh.metaobject.entities;

import java.util.HashSet;

/**
 *  добавляем в hashSetStr guid для дальнейшего кэщирования
 */
public interface IAddCashRefs {

    /**
     * Добавляет необходимые ссылки
     * @param hashSetStr
     */
    public void addRefs(HashSet<String> hashSetStr);

}
