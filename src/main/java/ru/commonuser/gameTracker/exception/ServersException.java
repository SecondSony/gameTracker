package ru.commonuser.gameTracker.exception;

import ru.commonuser.gameTracker.exception.error.ErrorInformation;

public class ServersException extends Exception {
    private final ErrorInformation errorInformation;
    private String causeMessage;

    public ServersException(final ErrorInformation errorInformation) {
        super(errorInformation.getMessage());
        this.errorInformation = errorInformation;
    }

    public ServersException(final ErrorInformation errorInformation, Throwable cause) {
        super(errorInformation.getMessage(), cause);
        this.errorInformation = errorInformation;
        if (cause instanceof ServersException) {
            this.causeMessage = cause.getMessage();
        }
    }

    public String getFriendlyMessage() {
        return errorInformation.getFriendlyMessage() + (causeMessage != null ? ". " + causeMessage : "");
    }
}
