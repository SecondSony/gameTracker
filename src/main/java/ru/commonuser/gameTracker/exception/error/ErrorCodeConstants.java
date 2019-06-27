package ru.commonuser.gameTracker.exception.error;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeConstants {

    public static final Map<ErrorCodeIdentifier, String> messages = new HashMap<>();

    public static final ErrorCodeIdentifier BASE = new ErrorCodeIdentifier("0");
    public static final ErrorCodeIdentifier DATA_NOT_FOUND = BASE.branch("1");
    public static final ErrorCodeIdentifier ACCESS_ERROR = BASE.branch("2");
    public static final ErrorCodeIdentifier DATA_VALIDATE_ERROR = BASE.branch("3");

    public static final ErrorCodeIdentifier USER = new ErrorCodeIdentifier("2");
    public static final ErrorCodeIdentifier USER_LIST_ERROR = USER.branch("1");
    public static final ErrorCodeIdentifier USER_INFO_ERROR = USER.branch("2");

    public static final ErrorCodeIdentifier USER_ADD_ERROR = USER.branch("3");
    public static final ErrorCodeIdentifier USER_LOGIN_ALREADY_EXIST = USER_ADD_ERROR.branch("1");

    public static final ErrorCodeIdentifier USER_PASSWORD_ERROR = USER.branch("4");
    public static final ErrorCodeIdentifier USER_PASSWORD_LENGTH_ERROR = USER_PASSWORD_ERROR.branch("2");
    public static final ErrorCodeIdentifier USER_PASSWORD_COMPARE_ERROR = USER_PASSWORD_ERROR.branch("2");

    public static final ErrorCodeIdentifier USER_UPDATE_ERROR = USER.branch("5");

    public static final ErrorCodeIdentifier USER_BLOCK_ERROR = USER.branch("6");
    public static final ErrorCodeIdentifier USER_BLOCK_SELF_ERROR = USER_BLOCK_ERROR.branch("1");

    public static final ErrorCodeIdentifier USER_UNBLOCK_ERROR = USER.branch("7");

    static {
        messages.put(DATA_NOT_FOUND, "Ошибка при получении реестра ldap-серверов");
        messages.put(ACCESS_ERROR, "Доступ запрещен");
        messages.put(DATA_VALIDATE_ERROR, "Отправленные данные некорректны");

        messages.put(USER_LIST_ERROR, "Ошибка при получении реестра пользователей");
        messages.put(USER_INFO_ERROR, "Ошибка при получении пользователя");

        messages.put(USER_ADD_ERROR, "Ошибка при добавлении пользователя");
        messages.put(USER_LOGIN_ALREADY_EXIST, "Логин занят другим пользователем");

        messages.put(USER_PASSWORD_ERROR, "Ошибка при изменении пароля пользователя");
        messages.put(USER_PASSWORD_LENGTH_ERROR, "Некорректная длина пароля");
        messages.put(USER_PASSWORD_COMPARE_ERROR, "Пароли не совпадают");

        messages.put(USER_UPDATE_ERROR, "Ошибка при изменении пользователя");

        messages.put(USER_BLOCK_ERROR, "Ошибка при блокировке пользователя");
        messages.put(USER_BLOCK_SELF_ERROR, "Нельзя заблокировать себя");

        messages.put(USER_UNBLOCK_ERROR, "Ошибка при разблокировке пользователя");
    }
}
