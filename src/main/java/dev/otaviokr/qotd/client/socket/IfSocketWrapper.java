package dev.otaviokr.qotd.client.socket;

import dev.otaviokr.qotd.client.exception.ClientSocketWrapperException;

public interface IfSocketWrapper {
    public String getHostname();
    public int getPort();
    public void close() throws ClientSocketWrapperException;
    public void connect() throws ClientSocketWrapperException;
    public void connect(String hostname, int port) throws ClientSocketWrapperException;
    public String readLineFromServer() throws ClientSocketWrapperException;
    public void sendToServer(String text) throws ClientSocketWrapperException;
}
