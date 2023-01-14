package com.company.Chat.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private final Socket socket;
    private final Server server;
    private BufferedReader in;
    private PrintWriter out;
    private UserState userState;

    private UsersManager user;

    ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        userState = new NoAuthUser(this);
        user = new UsersManager();
    }

    @Override
    public void run() {
        ServerLogger.log(socket.getInetAddress()+":"+socket.getPort()+" podłączył się.");

        while(!socket.isClosed()){
            String message = null;
            try {
                if((message = in.readLine()) == null){
                    user.logOut(this);
                    closeSocket();
                    continue;
                }
                userState.handleMessage(message);
            } catch (IOException e) {
                user.logOut(this);
                closeSocket();
            }
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    private void closeSocket(){
        ServerLogger.log(socket.getInetAddress()+":"+socket.getPort()+" rozłączył się.");
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Server getServer() {
        return server;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public UsersManager getUser() {
        return user;
    }

    public void setUser(UsersManager user) {
        this.user = user;
    }
}
