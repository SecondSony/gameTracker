package ru.commonuser.gameTracker.exception;


import ru.commonuser.gameTracker.exception.error.ErrorInformation;

public class NotFoundException extends ServerException {
    public NotFoundException(ErrorInformation errorInformation) {
        super(errorInformation);
    }
}
