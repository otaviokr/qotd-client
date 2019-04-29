package dev.otaviokr.qotd.client.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QotdClientExceptionTest {

    @Test
    public void testMessageAndThrowable() {
        String errorMsg = "This is a test error message";
        String causeMsg = "This is the cause error message";

        Throwable exception = assertThrows(QotdClientException.class, () -> {
            throw new QotdClientException(errorMsg, new UnsupportedOperationException(causeMsg));
        });

        assertEquals(errorMsg, exception.getMessage());
        assertEquals(causeMsg, exception.getCause().getMessage());
    }

    @Test
    public void testMessageOnly() {
        String errorMsg = "This is a test error message";

        Throwable exception = assertThrows(QotdClientException.class, () -> {
            throw new QotdClientException(errorMsg);
        });

        assertEquals(errorMsg, exception.getMessage());
    }
}
