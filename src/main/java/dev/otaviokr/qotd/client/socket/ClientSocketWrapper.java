package dev.otaviokr.qotd.client.socket;

import dev.otaviokr.qotd.client.exception.ClientSocketWrapperException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSocketWrapper implements IfSocketWrapper {
    private Socket socket;
    private String hostname;
    private int port;

    public ClientSocketWrapper(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void close() throws ClientSocketWrapperException {
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch(IOException e) {
                throw new ClientSocketWrapperException("Failed to close socket to " + this.hostname + ":" + this.port, e);
            }
        }
    }

    @Override
    public void connect() throws ClientSocketWrapperException {
        connect(this.hostname, this.port);
    }

    @Override
    public void connect(String hostname, int port) throws ClientSocketWrapperException {
        if (this.socket != null && this.socket.isConnected()) {
            String msg = "Socket already connected to %s:%d . You must close it before attempting a new connection.";
            throw new ClientSocketWrapperException(String.format(msg, hostname, port));
        }

        try {
            this.socket = new Socket(hostname, port);
        } catch (IOException e) {
            throw new ClientSocketWrapperException("Failed to connect to " + hostname + ":" + port, e);
        }
    }

    @Override
    public void sendToServer(String text) throws ClientSocketWrapperException {
        if (!checkConnection()) {
            throw new ClientSocketWrapperException("Cannot send while disconnected");
        }

        DataOutputStream outToServer;
        try {
            outToServer = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            throw new ClientSocketWrapperException("Error while retrieving socket output stream. Data not sent.", e);
        }
        try {
            outToServer.writeChars(text);
        } catch (IOException e) {
            throw new ClientSocketWrapperException("Error while writing to output stream Data not sent.", e);
        }
        try {
            outToServer.flush();
        } catch (IOException e) {
            throw new ClientSocketWrapperException("Error while flushing socket output stream Data not sent.", e);
        }
    }

    @Override
    public String readLineFromServer() throws ClientSocketWrapperException {
        if (!checkConnection()) {
            throw new ClientSocketWrapperException("Cannot read while disconnected");
        }

        try {
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            return inFromServer.readLine();
        } catch (IOException e) {
            throw new ClientSocketWrapperException("Error while reading data from " + hostname + ":" + port, e);
        }
    }

    public boolean checkConnection() {
        return this.socket.isConnected();
    }
}
