package dev.otaviokr.qotd.client.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientSocketWrapperExceptionTest {

    @Test
    public void testMessageAndThrowable() {
        String errorMsg = "This is a test error message";
        String causeMsg = "This is the cause error message";

        Throwable exception = assertThrows(ClientSocketWrapperException.class, () -> {
            throw new ClientSocketWrapperException(errorMsg, new UnsupportedOperationException(causeMsg));
        });

        assertEquals(exception.getMessage(), errorMsg);
        assertEquals(exception.getCause().getMessage(), causeMsg);
    }

    @Test
    public void testMessageOnly() {
        String errorMsg = "This is a test error message";

        Throwable exception = assertThrows(ClientSocketWrapperException.class, () -> {
            throw new ClientSocketWrapperException(errorMsg);
        });

        assertEquals(exception.getMessage(), errorMsg);
    }
}
