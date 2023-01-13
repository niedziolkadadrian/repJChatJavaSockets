package com.company.Chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    public Server(Integer port)  {
        this.port = port;
    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch(IOException ioe) {
            ServerLogger.log("Error opening socket. " + ioe.getMessage());
        }
        ServerLogger.log("Server wystartował.");
        ServerLogger.log("Port: "+serverSocket.getLocalPort());
        while (true){
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler cliThread = new ClientHandler(clientSocket);
                cliThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Server server = new Server(8188);
        server.run();
    }
}
