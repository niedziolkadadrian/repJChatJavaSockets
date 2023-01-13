package com.company.Chat.Server;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    Socket socket;
    ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        ServerLogger.log(socket.getInetAddress()+":"+socket.getPort()+" podłączył się.");

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
