package ru.commonuser.gameTracker.exception;

import ru.commonuser.gameTracker.exception.error.ErrorInformation;

public class InvalidDataException extends ServersException {

    public InvalidDataException(ErrorInformation errorInformation) {
        super(errorInformation);
    }
}

