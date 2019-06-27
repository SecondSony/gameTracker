package ru.commonuser.gameTracker.exception;

import ru.commonuser.gameTracker.exception.error.ErrorInformation;

public class AlreadyExistsException extends ServersException {

    public AlreadyExistsException(ErrorInformation errorInformation)
    {
        super(errorInformation);
    }
}
