package dev.otaviokr.qotd.client.exception;

import java.io.IOException;

public class QotdClientException extends Throwable {
    public QotdClientException(String e) {
        super(e);
    }

    public QotdClientException(String message, Throwable e) {
        super(message, e);
    }
}
