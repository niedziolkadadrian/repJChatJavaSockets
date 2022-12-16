package com.company.Chat.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedByInterruptException;
import java.util.Arrays;

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
            System.err.println("error opening socket. " + ioe.getMessage());
        }
        System.out.println("Server wystartował.");
        System.out.println("Port: "+serverSocket.getLocalPort());
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

    public static void main(String[] args) throws IOException {
        Server server = new Server(8188);
        server.run();
    }
}
