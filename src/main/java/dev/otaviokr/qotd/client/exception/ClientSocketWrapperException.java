package dev.otaviokr.qotd.client.exception;

public class ClientSocketWrapperException extends Throwable {
    public ClientSocketWrapperException(String message, Throwable e) {
        super(message, e);
    }

    public ClientSocketWrapperException(String message) {
        super(message);
    }
}
