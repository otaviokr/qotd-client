package dev.otaviokr.qotd.client;

import dev.otaviokr.qotd.client.exception.ClientSocketWrapperException;
import dev.otaviokr.qotd.client.exception.QotdClientException;
import dev.otaviokr.qotd.client.socket.ClientSocketWrapper;
import dev.otaviokr.qotd.client.socket.IfSocketWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ClientMain {

    private Properties props = new Properties();
    private IfSocketWrapper socket = null;

    private static final Logger log = LogManager.getLogger(ClientMain.class);

    public ClientMain() throws QotdClientException {
        ClassLoader classLoader = this.getClass().getClassLoader();

        InputStream inputFile = classLoader.getResourceAsStream("qotd-client-test.properties");
        if (inputFile == null) { inputFile = classLoader.getResourceAsStream("qotd-client.properties"); }
        if (inputFile == null) { inputFile = classLoader.getResourceAsStream("qotd-test.properties"); }
        if (inputFile == null) { inputFile = classLoader.getResourceAsStream("qotd.properties"); }

        if (null != inputFile) {
            try {
                props.load(inputFile);
            } catch (IOException e) {
                throw new QotdClientException("Error while loading properties from file", e);
            }
        } else {
            String exception = "Unable to find qotd-client.properties in classpath!";
            System.out.println(exception);
            throw new QotdClientException(exception);
        }
    }

    public void setSocket(IfSocketWrapper socket) {
        this.socket = socket;
    }

    public void close() throws ClientSocketWrapperException {
        this.socket.close();
    }

    public String getServerHostname() {
        return props.getProperty("server.hostname", "UNKNOWN");
    }

    public int getServerPort() {
        return Integer.parseInt(props.getProperty("server.port", "-1"));
    }

    public String readLine() throws ClientSocketWrapperException {
        return this.socket.readLineFromServer();
    }

    public void connect() throws ClientSocketWrapperException {
        if(this.socket == null ||
                getServerHostname().compareToIgnoreCase(socket.getHostname()) != 0 ||
                getServerPort() != socket.getPort()) {
            this.socket = new ClientSocketWrapper(getServerHostname(), getServerPort());
        }
        this.socket.connect();
    }

    public String getQuoteOfTheDay() throws ClientSocketWrapperException {
        log.info("Connecting to " + getServerHostname() + ":" + getServerPort());
        connect();

        log.debug("Reading incoming data...");
        String qotd = readLine();

        log.debug("Received: "+ qotd);

        close();
        log.debug("Socket closed.");

        return qotd;
    }

    public static void main(String[] argv) throws QotdClientException, ClientSocketWrapperException {

        log.info("Starting QOTD dev...");
        ClientMain client = new ClientMain();

        String quote = client.getQuoteOfTheDay();
        System.out.println(quote);
    }
}
