package ru.commonuser.gameTracker.dto;

import java.io.Serializable;

public interface ObjectWrapper<T> extends Serializable
{
    /**
     * Записывает данные в обёртку
     * @param item Информация о ссылке
     */
    void toWrapper(T item);

    /**
     * Получает данные из обёртки
     * @param item Информация о ссылке
     */
    void fromWrapper(T item);
}
