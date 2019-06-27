package ru.commonuser.gameTracker.exception;

import ru.commonuser.gameTracker.exception.error.ErrorInformation;

public class InvalidDataException extends ServerException {

    public InvalidDataException(ErrorInformation errorInformation) {
        super(errorInformation);
    }
}

