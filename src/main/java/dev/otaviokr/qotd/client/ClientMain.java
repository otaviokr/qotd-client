package dev.otaviokr.qotd.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMain {

    private static final Logger log = LogManager.getLogger(ClientMain.class);

    public static void main(String argv[]) throws Exception {
//        System.setProperty("log4j2.configurationFile",
//                "D:\\Development\\intellij-projects\\qotdclient\\src\\main\\resources\\log4j2.yml");
        String hostname = "localhost";
        int port = 17111;

        log.info("Starting QOTD dev...");
        log.info("Connecting to " + hostname + ":" + port);
        Socket clientSocket = new Socket(hostname, port);

//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        log.debug("Reading incoming data...");
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String quote = inFromServer.readLine();
        log.error("Received: " + quote);
        log.info("Received: " + quote);
        System.out.println(( "********" + quote));
        clientSocket.close();
        log.debug("Socket closed.");
    }
}
