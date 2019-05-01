package dev.otaviokr.qotd.client;

import dev.otaviokr.qotd.client.exception.ClientSocketWrapperException;
import dev.otaviokr.qotd.client.socket.ClientSocketWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

@ExtendWith(MockitoExtension.class)
public class ClientMainTest {

    @InjectMocks
    private ClientMain clientMain;

    @Mock
    private ClientSocketWrapper clientSocketWrapper;

    @BeforeAll
    static public void setup() {
        initMocks(ClientMainTest.class);
    }

    @Test
    public void testClose() throws ClientSocketWrapperException {
        ClientMain clientMain = mock(ClientMain.class);

        clientMain.close();
    }

    @Test
    public void testConnect() throws ClientSocketWrapperException {
        ClientMain clientMain = mock(ClientMain.class);

        clientMain.connect();
    }

    @Test
    public void testGetServerHostname() {
        ClientMain clientMain = mock(ClientMain.class);

        clientMain.getServerHostname();
    }

    @Test
    public void testGetServerPort() {
        ClientMain clientMain = mock(ClientMain.class);

        clientMain.getServerPort();
    }

    @Test
    public void testReadLine() throws ClientSocketWrapperException {
        String expected = "This is a test";
        doReturn(expected).when(clientSocketWrapper).readLineFromServer();
        //clientMain = mock(ClientMain.class);


        String actual = clientMain.readLine();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetQuoteOfTheDay() throws ClientSocketWrapperException, IOException {

        InputStream inputFile = this.getClass().getClassLoader().getResourceAsStream("qotd-client-test.properties");
        Properties props = new Properties();
        props.load(inputFile);

        String hostname = props.getProperty("server.hostname", "NOT_FOUND");
        int port = Integer.parseInt(props.getProperty("server.port", "-1"));
        String expected = "This is a test";
        doNothing().when(clientSocketWrapper).connect();
        doReturn(hostname).when(clientSocketWrapper).getHostname();
        doReturn(port).when(clientSocketWrapper).getPort();
        doReturn(expected).when(clientSocketWrapper).readLineFromServer();
        doNothing().when(clientSocketWrapper).close();
        //ClientMain clientMain = mock(ClientMain.class);

        String actual = clientMain.getQuoteOfTheDay();
        assertEquals(expected, actual);
    }

    @Test
    public  void testMain() {

    }
}
