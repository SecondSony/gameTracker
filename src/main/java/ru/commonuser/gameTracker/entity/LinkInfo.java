package ru.commonuser.gameTracker.entity;

import lombok.Data;

@Data
public class LinkInfo {
    /**
     * Название ссылки на файл
     */
    private String name;

    /**
     * Ссылка на сайт источника
     */
    private String url;

    /**
     * Ссылка на источник
     */
    private String urlLink;
}
