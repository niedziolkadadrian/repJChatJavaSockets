package com.company.Chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Server implements Runnable{
    private final int port;
    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, CopyOnWriteArrayList<ClientHandler>> usersConns;
    public Server(Integer port)  {
        this.port = port;
        usersConns = new ConcurrentHashMap<>();
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
        while (!serverSocket.isClosed()){
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler cliThread = new ClientHandler(clientSocket, this);
                cliThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addUserConn(String user, ClientHandler conn){
        if(!usersConns.containsKey(user)){
            usersConns.put(user,new CopyOnWriteArrayList<>());
            broadcast("ACTION USER_CONNECTED "+user);
        }
        usersConns.get(user).add(conn);
        sendListOfConnectedUsers(conn);
        ServerLogger.log("Użytkownik "+user+" zalogował się ["+
                conn.getSocket().getInetAddress()+":"+conn.getSocket().getPort()+"].");
    }

    private void sendListOfConnectedUsers(ClientHandler conn) {
        for (String other:usersConns.keySet()) {
            conn.sendMessage("ACTION USER_CONNECTED "+other);
        }
    }

    public void delUserConn(String user, ClientHandler conn){
        if(usersConns.containsKey(user)){
            usersConns.get(user).remove(conn);

            ServerLogger.log("Użytkownik "+user+" wylogował się ["+
                    conn.getSocket().getInetAddress()+":"+conn.getSocket().getPort()+"].");

            if(usersConns.get(user).isEmpty()){
                usersConns.remove(user);
                broadcast("ACTION USER_DISCONNECTED "+user);
            }
        }
    }

    public void broadcast(String message){
        for (List<ClientHandler> userConns:usersConns.values()) {
            for (ClientHandler conn:userConns) {
                conn.sendMessage(message);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8188);
        server.run();
    }
}
