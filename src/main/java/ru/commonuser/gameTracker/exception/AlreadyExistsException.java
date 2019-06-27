package ru.commonuser.gameTracker.exception;

import ru.commonuser.gameTracker.exception.error.ErrorInformation;

public class AlreadyExistsException extends ServerException {

    public AlreadyExistsException(ErrorInformation errorInformation)
    {
        super(errorInformation);
    }
}
